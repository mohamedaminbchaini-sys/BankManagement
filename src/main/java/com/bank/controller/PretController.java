package com.bank.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.bank.model.Pret;
import com.bank.service.PretService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prets")
public class PretController {

    private final PretService pretService;

    public PretController(PretService pretService) {
        this.pretService = pretService;
    }

    @GetMapping
    public List<Pret> getAll() {
        return pretService.findAll();
    }

    @GetMapping("/{id}")
    public Pret getById(@PathVariable Long id) {
        return pretService.findById(id);
    }

    // Requête personnalisée 3
    @GetMapping("/en-attente")
    public List<Pret> enAttente() {
        return pretService.findPretsEnAttente();
    }

    @PostMapping
    public ResponseEntity<Pret> demander(@Valid @RequestBody Pret pret) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pretService.demander(pret));
    }

    @PutMapping("/{id}")
    public Pret update(@PathVariable Long id, @Valid @RequestBody Pret pret) {
        pretService.findById(id);
        pret.setId(id);
        return pretService.save(pret);
    }

    @PutMapping("/{id}/valider")
    public Pret valider(@PathVariable Long id) {
        return pretService.valider(id);
    }

    @PutMapping("/{id}/refuser")
    public Pret refuser(@PathVariable Long id) {
        return pretService.refuser(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pretService.findById(id);
        pretService.delete(id);
        return ResponseEntity.noContent().build();
    }
}