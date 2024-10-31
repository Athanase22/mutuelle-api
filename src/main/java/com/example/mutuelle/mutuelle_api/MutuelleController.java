package com.example.mutuelle.mutuelle_api;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/mutuelles")
public class MutuelleController {


    @Autowired
    private MutuelleRepository mutuelleRepository;

    // Recuperer la liste des Mutuelles
    @GetMapping
    public List<Mutuelle> getAllMutuelles(){
        return mutuelleRepository.findAll();
    }

    // Creer une nouvelle mutuelle
    @PostMapping
    public Mutuelle createMutuelle(@RequestBody Mutuelle mutuelle){
        return mutuelleRepository.save(mutuelle);
    }

    // Ajouter plusieurs mutuelles
    @PostMapping("/batch")
    public List<Mutuelle> createMutuelles(@RequestBody List<Mutuelle> mutuelles) {
        return mutuelleRepository.saveAll(mutuelles);
    }


    // Recuperer la mutuelle par son id
    @GetMapping("/id/{id}")
    public Mutuelle getMutuelleById(@PathVariable Long id) {
        return mutuelleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mutuelle introuvable"));
    }

    // mettre a jour les informations de  la mutuelle
    @PutMapping("/{id}")
    public Mutuelle updateMutuelle(@PathVariable Long id, @RequestBody Mutuelle mutuelleDetails){
        Mutuelle mutuelle = mutuelleRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Mutuelle Introuvable"));
        mutuelle.setNom(mutuelleDetails.getNom());
        mutuelle.setAdresse(mutuelleDetails.getAdresse());
        mutuelle.setContact(mutuelleDetails.getContact());

        return mutuelleRepository.save(mutuelle);
    }

    //Supprimer une mutuelle
    @DeleteMapping("/{id}")
    public String deleteMutuelle(@PathVariable Long id){
        Mutuelle mutuelle = mutuelleRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Mutuelle introuvable"));
        mutuelleRepository.delete(mutuelle);
        return "Mutuelle supprimée avec succes";

    }

    // Rechercher les mutuelles par nom
    @GetMapping("/search")
    public List<Mutuelle> searchMutuellesByNom(@RequestParam String nom){
        return mutuelleRepository.findByNomContainingIgnoreCase(nom);
    }

    // Rechercher les mutuelles par adresse
    @GetMapping("/adresse")
    public List<Mutuelle> getMutuellesByAdrsse(@RequestParam String adresse){
        return mutuelleRepository.findByAdresse(adresse);
    }

    // Rechercher par nom et adresse (requete complexe)

    @GetMapping("/search/advanced")
    public List<Mutuelle> searchByNomAndAdresse(@RequestParam String nom, @RequestParam String adresse){
        return mutuelleRepository.searchByNomAndAdresse(nom, adresse);
    }

    //Rechercher le nombre total de mutuelles

    @GetMapping("/count")
    public long countMutuelles() {
        return mutuelleRepository.count();
    }

    //Trier les mutuelles par nom
    @GetMapping("/sorted")
    public List<Mutuelle> getSortedMutuelles(@RequestParam(defaultValue = "nom") String sortBy) {
        List<String> validSortFields = Arrays.asList("nom", "adresse"); // Ajoute d'autres attributs si nécessaire
        if (!validSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Champs de tri non valide : " + sortBy);
        }
        return mutuelleRepository.findAll(Sort.by(sortBy));
    }


}
