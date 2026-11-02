package com.bank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.model.Pret;
import com.bank.repository.PretRepository;

@Service
public class PretService {

    private final PretRepository pretRepository;

    public PretService(PretRepository pretRepository) {
        this.pretRepository = pretRepository;
    }

    public List<Pret> findAll() {
        return pretRepository.findAll();
    }

    public Pret findById(Long id) {
        return pretRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pret introuvable : " + id));
    }

    public List<Pret> findPretsEnAttente() {
        return pretRepository.findPretsEnAttente();
    }

    // Une nouvelle demande de prêt commence toujours EN_ATTENTE
    public Pret demander(Pret pret) {
        pret.setStatut("EN_ATTENTE");
        return pretRepository.save(pret);
    }

    public Pret save(Pret pret) {
        return pretRepository.save(pret);
    }

    public void delete(Long id) {
        pretRepository.deleteById(id);
    }

    @Transactional
    public Pret valider(Long id) {
        Pret pret = findById(id);
        if (!"EN_ATTENTE".equals(pret.getStatut())) {
            throw new RuntimeException("Seul un prêt EN_ATTENTE peut être validé");
        }
        pret.setStatut("VALIDE");
        return pretRepository.save(pret);
    }

    @Transactional
    public Pret refuser(Long id) {
        Pret pret = findById(id);
        if (!"EN_ATTENTE".equals(pret.getStatut())) {
            throw new RuntimeException("Seul un prêt EN_ATTENTE peut être refusé");
        }
        pret.setStatut("REFUSE");
        return pretRepository.save(pret);
    }
}