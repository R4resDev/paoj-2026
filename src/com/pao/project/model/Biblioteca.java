package com.pao.project.model;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private String nume;
    private List<Sectiune> sectiuni = new ArrayList<>();
    private List<Carte> carti = new ArrayList<>();
    private List<Cititor> cititori = new ArrayList<>();
    private List<Imprumut> imprumuturi = new ArrayList<>();

    public Biblioteca(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public List<Sectiune> getSectiuni() {
        return sectiuni;
    }

    public List<Carte> getCarti() {
        return carti;
    }

    public List<Cititor> getCititori() {
        return cititori;
    }

    public List<Imprumut> getImprumuturi() {
        return imprumuturi;
    }

    public void adaugaSectiune(Sectiune sectiune) {
        if (!sectiuni.contains(sectiune)) {
            sectiuni.add(sectiune);
        }
    }

    public void adaugaCarte(Carte carte) {
        if (!carti.contains(carte)) {
            carti.add(carte);
        }
    }

    public void adaugaCititor(Cititor cititor) {
        if (!cititori.contains(cititor)) {
            cititori.add(cititor);
        }
    }

    public void adaugaImprumut(Imprumut imprumut) {
        imprumuturi.add(imprumut);
    }

    @Override
    public String toString() {
        return "Biblioteca{nume='" + nume + "', sectiuni=" + sectiuni.size() +
                ", carti=" + carti.size() + ", cititori=" + cititori.size() +
                ", imprumuturi=" + imprumuturi.size() + "}";
    }
}