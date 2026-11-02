package com.bank.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.model.Compte;

public interface CompteRepository extends JpaRepository<Compte, Long> {

    @Query("SELECT c FROM Compte c WHERE c.solde > :montant")
    List<Compte> findBySoldeSuperieurA(@Param("montant") BigDecimal montant);
}