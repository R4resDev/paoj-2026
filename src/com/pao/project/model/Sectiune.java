package com.pao.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sectiune {
    private String nume;
    private List<Carte> carti = new ArrayList<>();

    public Sectiune(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public List<Carte> getCarti() {
        return carti;
    }

    public void adaugaCarte(Carte c) {
        if (!carti.contains(c)) {
            carti.add(c);
        }
    }

    @Override
    public String toString() {
        return "Sectiune{nume='" + nume + "', carti=" + carti + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sectiune)) return false;
        Sectiune sectiune = (Sectiune) o;
        return Objects.equals(nume, sectiune.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume);
    }
}