package com.pao.project.model;

public class Imprumut {
    private Carte carte;
    private Cititor cititor;
    private int id;
    private boolean activ = true;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    @Override
    public String toString() {
        return "Imprumut{carte=" + carte.getTitlu() + ", cititor=" + cititor.getNume() + "}";
    }
}