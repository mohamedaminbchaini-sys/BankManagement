package com.bank.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.exception.ResourceNotFoundException;

public class CrudService<T> {

    private final JpaRepository<T, Long> repository;
    private final String nomEntite;

    public CrudService(JpaRepository<T, Long> repository, String nomEntite) {
        this.repository = repository;
        this.nomEntite = nomEntite;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(nomEntite + " introuvable : " + id));
    }

    public T save(T entite) {
        return repository.save(entite);
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}