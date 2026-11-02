package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.Agence;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}