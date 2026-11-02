package com.bank.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Compte;
import com.bank.model.Transaction;
import com.bank.repository.TransactionRepository;
import com.bank.service.CompteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    private final CompteService compteService;
    private final TransactionRepository transactionRepository;

    public CompteController(CompteService compteService,
                            TransactionRepository transactionRepository) {
        this.compteService = compteService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public List<Compte> getAll() {
        return compteService.findAll();
    }

    @GetMapping("/{id}")
    public Compte getById(@PathVariable Long id) {
        return compteService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Compte> create(@Valid @RequestBody Compte compte) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compteService.save(compte));
    }

    @PutMapping("/{id}")
    public Compte update(@PathVariable Long id, @Valid @RequestBody Compte compte) {
        compteService.findById(id);
        compte.setId(id);
        return compteService.save(compte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Requête personnalisée 1
    @GetMapping("/solde-superieur")
    public List<Compte> soldeSuperieur(@RequestParam BigDecimal montant) {
        return compteService.findBySoldeSuperieurA(montant);
    }

    // Requête personnalisée 2
    @GetMapping("/{id}/transactions")
    public List<Transaction> transactions(@PathVariable Long id) {
        return transactionRepository.findByCompteId(id);
    }

    @PostMapping("/{id}/depot")
    public Transaction depot(@PathVariable Long id, @RequestParam BigDecimal montant) {
        return compteService.deposer(id, montant);
    }

    @PostMapping("/{id}/retrait")
    public Transaction retrait(@PathVariable Long id, @RequestParam BigDecimal montant) {
        return compteService.retirer(id, montant);
    }

    @PostMapping("/virement")
    public Transaction virement(@RequestParam Long source,
                                @RequestParam Long destination,
                                @RequestParam BigDecimal montant) {
        return compteService.virement(source, destination, montant);
    }
}