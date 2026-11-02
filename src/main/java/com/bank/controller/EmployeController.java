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

import com.bank.model.Employe;
import com.bank.repository.EmployeRepository;
import com.bank.service.CrudService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final CrudService<Employe> service;

    public EmployeController(EmployeRepository repository) {
        this.service = new CrudService<>(repository, "Employe");
    }

    @GetMapping
    public List<Employe> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Employe getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Employe> create(@Valid @RequestBody Employe employe) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(employe));
    }

    @PutMapping("/{id}")
    public Employe update(@PathVariable Long id, @Valid @RequestBody Employe employe) {
        service.findById(id);
        employe.setId(id);
        return service.save(employe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}