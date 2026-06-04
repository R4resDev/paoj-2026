package com.pao.project.service;

import com.pao.project.model.Autor;
import com.pao.project.repository.AutorRepository;

import java.sql.SQLException;
import java.util.List;

public class AutorService {
    private static AutorService instance;
    private final AutorRepository autorRepository = new AutorRepository();

    private AutorService() {}

    public static AutorService getInstance() {
        if (instance == null) {
            instance = new AutorService();
        }
        return instance;
    }

    public void adaugaAutor(Autor autor) {
        try {
            autorRepository.save(autor);
            AuditService.getInstance().logAction("adauga_autor");
        } catch (SQLException e) {
            System.err.println("Eroare la adaugare autor: " + e.getMessage());
        }
    }

    public List<Autor> listeazaAutori() {
        AuditService.getInstance().logAction("listeaza_autori");
        try {
            return autorRepository.findAll();
        } catch (SQLException e) {
            System.err.println("Eroare la listare autori: " + e.getMessage());
            return null;
        }
    }
}