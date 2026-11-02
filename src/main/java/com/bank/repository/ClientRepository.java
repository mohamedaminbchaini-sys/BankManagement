package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}