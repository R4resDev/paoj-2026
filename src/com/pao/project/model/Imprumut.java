package com.pao.project.model;

public class Imprumut {
    private Carte carte;
    private Cititor cititor;

    public Imprumut(Carte carte, Cititor cititor) {
        this.carte = carte;
        this.cititor = cititor;
    }

    public Carte getCarte() {
        return carte;
    }

    public Cititor getCititor() {
        return cititor;
    }

    @Override
    public String toString() {
        return "Imprumut{carte=" + carte.getTitlu() + ", cititor=" + cititor.getNume() + "}";
    }
}