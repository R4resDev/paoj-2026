package com.pao.project.service;

import com.pao.project.exception.CititorNegasitException;
import com.pao.project.model.Cititor;
import com.pao.project.repository.CititorRepository;

import java.sql.SQLException;
import java.util.List;

public class CititorService {
    private static CititorService instance;
    private final CititorRepository cititorRepository = new CititorRepository(); // Legătura cu DB

    private CititorService() {}

    public static CititorService getInstance() {
        if (instance == null) {
            instance = new CititorService();
        }
        return instance;
    }

    public void adaugaCititor(Cititor cititor) {
        try {
            cititorRepository.save(cititor);
            AuditService.getInstance().logAction("adauga_cititor");
        } catch (SQLException e) {
            System.err.println("Eroare la adaugare cititor DB: " + e.getMessage());
        }
    }

    public void stergeCititor(int id) {
        try {
            cititorRepository.delete(id);
            AuditService.getInstance().logAction("sterge_cititor");
        } catch (SQLException e) {
            System.err.println("Eroare la stergere cititor DB: " + e.getMessage());
        }
    }

    public Cititor cautaDupaId(int id) {
        AuditService.getInstance().logAction("cauta_cititor_dupa_id");
        try {
            return cititorRepository.findById(id)
                    .orElseThrow(() -> new CititorNegasitException("Cititorul cu id=" + id + " nu a fost gasit in DB"));
        } catch (SQLException e) {
            throw new RuntimeException("Eroare DB", e);
        }
    }

    public List<Cititor> listeazaCititori() {
        AuditService.getInstance().logAction("listeaza_cititori");
        try {
            return cititorRepository.findAll();
        } catch (SQLException e) {
            System.err.println("Eroare DB: " + e.getMessage());
            return null;
        }
    }
}