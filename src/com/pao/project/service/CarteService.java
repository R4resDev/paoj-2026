package com.pao.project.service;

import com.pao.project.model.Carte;
import com.pao.project.repository.CarteRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CarteService {
    private static CarteService instance;
    private final CarteRepository carteRepository = new CarteRepository();

    private CarteService() {}

    public static CarteService getInstance() {
        if (instance == null) {
            instance = new CarteService();
        }
        return instance;
    }

    public void adaugaCarte(Carte carte) {
        try {
            carteRepository.save(carte);
            AuditService.getInstance().logAction("adauga_carte");
        } catch (SQLException e) {
            System.err.println("Eroare la adaugare carte DB: " + e.getMessage());
        }
    }

    public void stergeCarte(int id) {
        try {
            carteRepository.delete(id);
            AuditService.getInstance().logAction("sterge_carte");
        } catch (SQLException e) {
             System.err.println("Eroare la stergere carte DB: " + e.getMessage());
        }
    }

    public List<Carte> listeazaCarti() {
        AuditService.getInstance().logAction("listeaza_carti");
        try {
            List<Carte> lista = carteRepository.findAll();
            Collections.sort(lista);
            return lista;
        } catch (SQLException e) {
            System.err.println("Eroare DB: " + e.getMessage());
            return null;
        }
    }
}