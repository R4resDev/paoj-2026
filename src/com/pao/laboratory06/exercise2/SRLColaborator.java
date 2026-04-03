package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica {
    private double cheltuieliLunare;

    @Override
    public void citeste(Scanner s) {
        nume = s.next();
        prenume = s.next();
        venitBrutLunar = s.nextDouble();
        cheltuieliLunare = s.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        return (venitBrutLunar - cheltuieliLunare) * 12 * 0.84;
    }

    @Override
    public void afiseaza() {
        System.out.println("SRL: " + nume + " " + prenume + ", venit net anual: " + String.format("%.2f", calculeazaVenitNetAnual()) + " lei");
    }

    @Override
    public String tipContract() {
        return "SRL";
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.SRL;
    }
}