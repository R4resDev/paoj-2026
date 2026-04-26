package com.pao.project.model;

public abstract class AngajatBiblioteca extends Persoana {
    private double salariu;

    protected AngajatBiblioteca(String nume, double salariu) {
        super(nume);
        this.salariu = salariu;
    }

    public double getSalariu() {
        return salariu;
    }

    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    @Override
    public String toString() {
        return getRol() + "{nume='" + getNume() + "', salariu=" + salariu + "}";
    }
}