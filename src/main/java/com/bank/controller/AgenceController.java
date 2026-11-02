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

import com.bank.model.Agence;
import com.bank.repository.AgenceRepository;
import com.bank.service.CrudService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agences")
public class AgenceController {

    private final CrudService<Agence> service;

    public AgenceController(AgenceRepository repository) {
        this.service = new CrudService<>(repository, "Agence");
    }

    @GetMapping
    public List<Agence> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Agence getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Agence> create(@Valid @RequestBody Agence agence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(agence));
    }

    @PutMapping("/{id}")
    public Agence update(@PathVariable Long id, @Valid @RequestBody Agence agence) {
        service.findById(id);
        agence.setId(id);
        return service.save(agence);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}