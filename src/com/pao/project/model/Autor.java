package com.pao.project.model;

import java.util.Objects;

public class Autor extends Persoana {
    private int id;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public Autor(String nume) {
        super(nume);
    }

    @Override
    public String getRol() {
        return "Autor";
    }

    @Override
    public String toString() {
        return "Autor{nume='" + getNume() + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return Objects.equals(getNume(), autor.getNume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume());
    }
}