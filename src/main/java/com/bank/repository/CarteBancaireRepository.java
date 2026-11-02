package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.CarteBancaire;

public interface CarteBancaireRepository extends JpaRepository<CarteBancaire, Long> {
}
