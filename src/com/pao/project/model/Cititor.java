package com.pao.project.model;

import java.util.Objects;

public class Cititor extends Persoana {
    private int id;

    public Cititor(int id, String nume) {
        super(nume);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getRol() {
        return "Cititor";
    }

    @Override
    public String toString() {
        return "Cititor{id=" + id + ", nume='" + getNume() + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cititor)) return false;
        Cititor cititor = (Cititor) o;
        return id == cititor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}