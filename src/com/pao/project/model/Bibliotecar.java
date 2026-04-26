package com.pao.project.model;

public class Bibliotecar extends AngajatBiblioteca {
    private String departament;

    public Bibliotecar(String nume, double salariu, String departament) {
        super(nume, salariu);
        this.departament = departament;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    @Override
    public String getRol() {
        return "Bibliotecar";
    }

    @Override
    public String toString() {
        return "Bibliotecar{nume='" + getNume() + "', salariu=" + getSalariu() +
                ", departament='" + departament + "'}";
    }
}