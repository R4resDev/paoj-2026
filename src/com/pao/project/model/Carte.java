package com.pao.project.model;

import java.util.Objects;

public class Carte implements Comparable<Carte> {
    private String titlu;
    private Autor autor;
    private ISBN isbn;
    private boolean disponibila = true;
    private int id;
    private int autorId;

    public Carte(String titlu, Autor autor, ISBN isbn) {
        this.titlu = titlu;
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public boolean isDisponibila() {
        return disponibila;
    }

    public void setDisponibila(boolean disponibila) {
        this.disponibila = disponibila;
    }

    @Override
    public int compareTo(Carte other) {
        return this.titlu.compareToIgnoreCase(other.titlu);
    }

    @Override
    public String toString() {
        return "Carte{titlu='" + titlu + "', autor=" + autor + ", isbn=" + isbn +
                ", disponibila=" + disponibila + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carte)) return false;
        Carte carte = (Carte) o;
        return Objects.equals(isbn, carte.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}