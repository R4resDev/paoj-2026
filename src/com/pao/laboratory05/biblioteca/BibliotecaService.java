package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

// BibliotecaService.java — Singleton
// Constructor privat, getInstance() cu Holder intern (pattern din Lab 01)
// Câmp: private Carte[] carti (inițializat new Carte[0])
// void addCarte(Carte carte) — resize + adaugă + printează confirmare
// void listSortedByRating() — clonează, Arrays.sort(copy) (natural = Comparable), afișează
// void listSortedBy(Comparator<Carte> comparator) — clonează, Arrays.sort(copy, comparator), afișează

public class BibliotecaService {
    private static class Holder {
        private static final BibliotecaService instance = new BibliotecaService();
    }

    private Carte[] carti;

    private BibliotecaService() {
        this.carti = new Carte[0];
    }

    public static BibliotecaService getInstance() {
        return Holder.instance;
    }

    public void addCarte(Carte carte) {
        Carte[] copy = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, copy, 0, carti.length);
        copy[carti.length] = carte;
        carti = copy;
        System.out.println("Carte adăugată: " + carte.getTitlu());
    }

    public void listSortedByRating() {
        Carte[] copy = carti.clone();
        Arrays.sort(copy);
        int i = 1;
        for (Carte c : copy) {
            System.out.println(i + ". " + c);
            i++;
        }
    }

    public void listSortedBy(Comparator<Carte> comparator) {
        Carte[] copy = carti.clone();
        Arrays.sort(copy, comparator);
        int i = 1;
        for (Carte c : copy) {
            System.out.println(i + ". " + c);
            i++;
        }
    }
}
