package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public interface IOperatiiRW {
    void citeste(Scanner s);
    void afiseaza();
    String tipContract();
    default boolean areBonus() {
        return false;
    }
}