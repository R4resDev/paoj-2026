package com.pao.project.service;

import com.pao.project.model.Carte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarteService {
    private static CarteService instance;
    private final Map<String, Carte> carti = new HashMap<>();

    private CarteService() {
    }

    public static CarteService getInstance() {
        if (instance == null) {
            instance = new CarteService();
        }
        return instance;
    }

    public void adaugaCarte(Carte carte) {
        carti.put(carte.getTitlu(), carte);
    }

    public void stergeCarte(String titlu) {
        carti.remove(titlu);
    }

    public Carte cautaCarte(String titlu) {
        return carti.get(titlu);
    }

    public List<Carte> listeazaCarti() {
        List<Carte> lista = new ArrayList<>(carti.values());
        Collections.sort(lista);
        return lista;
    }

    public List<Carte> listeazaCartiDisponibile() {
        List<Carte> lista = new ArrayList<>();
        for (Carte carte : carti.values()) {
            if (carte.isDisponibila()) {
                lista.add(carte);
            }
        }
        Collections.sort(lista);
        return lista;
    }
}