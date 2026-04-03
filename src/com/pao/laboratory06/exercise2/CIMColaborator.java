package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica {
    private boolean bonus;

    @Override
    public void citeste(Scanner s) {
        nume = s.next();
        prenume = s.next();
        venitBrutLunar = s.nextDouble();

        if (s.hasNext()) {
            String b = s.next();
            bonus = b.equals("DA");
        }
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double baza = venitBrutLunar * 12 * 0.55;
        if (bonus) {
            baza *= 1.10;
        }
        return baza;
    }

    @Override
    public void afiseaza() {
        System.out.println("CIM: " + nume + " " + prenume + ", venit net anual: " + String.format("%.2f", calculeazaVenitNetAnual()) + " lei");
    }

    @Override
    public String tipContract() {
        return "CIM";
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }
}