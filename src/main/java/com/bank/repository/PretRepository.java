package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.model.Pret;

public interface PretRepository extends JpaRepository<Pret, Long> {

    @Query("SELECT p FROM Pret p WHERE p.statut = 'EN_ATTENTE'")
    List<Pret> findPretsEnAttente();
}