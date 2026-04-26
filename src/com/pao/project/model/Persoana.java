package com.pao.project.model;

public abstract class Persoana {
    private String nume;

    protected Persoana(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public abstract String getRol();
}