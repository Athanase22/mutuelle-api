package com.example.mutuelle.mutuelle_api;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface MutuelleRepository extends JpaRepository<Mutuelle, Long> {
    //Rechercer les mutuelles dont le nom contient une chaine spécifique
    List<Mutuelle> findByNomContainingIgnoreCase(String nom);

    // Rechercher les mutuelles par addresse
    List<Mutuelle> findByAdresse(String adresse);

    // Recherche avancée en utilisant les requetes JPQL
    @Query("SELECT m FROM Mutuelle m WHERE m.nom LIKE %:nom% AND m.adresse = :adresse")
    List<Mutuelle> searchByNomAndAdresse (@Param("nom") String nom, @Param("adresse") String adresse);

}
