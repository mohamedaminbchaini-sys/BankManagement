package com.bank.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.exception.BadRequestException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.Compte;
import com.bank.model.Transaction;
import com.bank.repository.CompteRepository;
import com.bank.repository.TransactionRepository;

@Service
public class CompteService {

    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;

    public CompteService(CompteRepository compteRepository,
                         TransactionRepository transactionRepository) {
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Compte> findAll() {
        return compteRepository.findAll();
    }

    public Compte findById(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte introuvable : " + id));
    }

    public Compte save(Compte compte) {
        return compteRepository.save(compte);
    }

    public void delete(Long id) {
        compteRepository.deleteById(id);
    }

    public List<Compte> findBySoldeSuperieurA(BigDecimal montant) {
        return compteRepository.findBySoldeSuperieurA(montant);
    }

    @Transactional
    public Transaction deposer(Long compteId, BigDecimal montant) {
        Compte compte = findById(compteId);
        compte.setSolde(compte.getSolde().add(montant));
        compteRepository.save(compte);

        Transaction t = new Transaction();
        t.setMontant(montant);
        t.setType("DEPOT");
        t.setCompteSource(compte);
        return transactionRepository.save(t);
    }

    @Transactional
    public Transaction retirer(Long compteId, BigDecimal montant) {
        Compte compte = findById(compteId);
        if (compte.getSolde().compareTo(montant) < 0) {
            throw new BadRequestException("Solde insuffisant");
        }
        compte.setSolde(compte.getSolde().subtract(montant));
        compteRepository.save(compte);

        Transaction t = new Transaction();
        t.setMontant(montant);
        t.setType("RETRAIT");
        t.setCompteSource(compte);
        return transactionRepository.save(t);
    }

    @Transactional
    public Transaction virement(Long sourceId, Long destinationId, BigDecimal montant) {
        Compte source = findById(sourceId);
        Compte destination = findById(destinationId);
        if (source.getSolde().compareTo(montant) < 0) {
            throw new BadRequestException("Solde insuffisant");
        }
        source.setSolde(source.getSolde().subtract(montant));
        destination.setSolde(destination.getSolde().add(montant));
        compteRepository.save(source);
        compteRepository.save(destination);

        Transaction t = new Transaction();
        t.setMontant(montant);
        t.setType("VIREMENT");
        t.setCompteSource(source);
        t.setCompteDestination(destination);
        return transactionRepository.save(t);
    }
}