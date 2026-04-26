package com.pao.project.service;

import com.pao.project.exception.CarteNedisponibilaException;
import com.pao.project.exception.CititorNegasitException;
import com.pao.project.model.Carte;
import com.pao.project.model.Cititor;
import com.pao.project.model.Imprumut;

import java.util.ArrayList;
import java.util.List;

public class ImprumutService {
    private static ImprumutService instance;
    private final List<Imprumut> imprumuturi = new ArrayList<>();

    private ImprumutService() {
    }

    public static ImprumutService getInstance() {
        if (instance == null) {
            instance = new ImprumutService();
        }
        return instance;
    }

    public void imprumuta(Carte carte, Cititor cititor) {
        if (cititor == null) {
            throw new CititorNegasitException("Cititor inexistent");
        }
        if (carte == null) {
            throw new IllegalArgumentException("Cartea nu exista");
        }
        if (!carte.isDisponibila()) {
            throw new CarteNedisponibilaException("Cartea nu este disponibila");
        }

        carte.setDisponibila(false);
        imprumuturi.add(new Imprumut(carte, cititor));
    }

    public void returneaza(Carte carte) {
        if (carte != null) {
            carte.setDisponibila(true);
        }
    }

    public List<Imprumut> getAll() {
        return imprumuturi;
    }

    public List<Imprumut> imprumuturiPentruCititor(int idCititor) {
        List<Imprumut> rezultat = new ArrayList<>();
        for (Imprumut imprumut : imprumuturi) {
            if (imprumut.getCititor().getId() == idCititor) {
                rezultat.add(imprumut);
            }
        }
        return rezultat;
    }
}