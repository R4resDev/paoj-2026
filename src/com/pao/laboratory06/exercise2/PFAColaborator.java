package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica {
    private double cheltuieliLunare;
    private static final double SalariuMinimAnual = 4050 * 12;

    @Override
    public void citeste(Scanner s) {
        nume = s.next();
        prenume = s.next();
        venitBrutLunar = s.nextDouble();
        cheltuieliLunare = s.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;
        double impozit = 0.10 * venitNet;

        double cass;
        if (venitNet < 6 * SalariuMinimAnual) {
            cass = 0.10 * (6 * SalariuMinimAnual);
        } else if (venitNet <= 72 * SalariuMinimAnual) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * SalariuMinimAnual);
        }

        double cas;
        if (venitNet < 12 * SalariuMinimAnual) {
            cas = 0;
        }
        else if (venitNet <= 24 * SalariuMinimAnual) {
            cas = 0.25 * (12 * SalariuMinimAnual);
        }
        else {
            cas = 0.25 * (24 * SalariuMinimAnual);
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public void afiseaza() {
        System.out.println("PFA: " + nume + " " + prenume + ", venit net anual: " + String.format("%.2f", calculeazaVenitNetAnual()) + " lei");
    }

    @Override
    public String tipContract() {
        return "PFA";
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
    }
}