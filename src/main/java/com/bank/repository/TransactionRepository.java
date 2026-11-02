package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.compteSource.id = :compteId OR t.compteDestination.id = :compteId ORDER BY t.date DESC")
    List<Transaction> findByCompteId(@Param("compteId") Long compteId);
}