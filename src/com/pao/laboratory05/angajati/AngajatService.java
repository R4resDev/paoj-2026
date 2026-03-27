package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {
    private static class Holder {
        private static final AngajatService instance = new AngajatService();
    }

    private Angajat[] angajati;

    private AngajatService() {
        this.angajati = new Angajat[0];
    }

    public static AngajatService getInstance() {
        return Holder.instance;
    }

    public void addAngajat(Angajat ang) {
        Angajat[] copy = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, copy, 0, angajati.length);
        copy[angajati.length] = ang;
        angajati = copy;
        System.out.println("Angajat adăugat: " + ang.getNume());
    }

    public void printAll() {
        for (Angajat ang : angajati) {
            System.out.println(ang);
        }
    }

    public void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);
        System.out.println("--- Angajați după salariu (descrescător) ---");
        int i = 1;
        for (Angajat ang : copy) {
            System.out.println(i + ". " + ang);
            i++;
        }
    }

    public void findByDepartament(String numeDept) {
        System.out.println("--- Angajați din " + numeDept + " ---");
        boolean ok = false;
        for (Angajat ang : angajati) {
            if (ang.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(ang);
                ok = true;
            }
        }
        if (ok == false) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }
}