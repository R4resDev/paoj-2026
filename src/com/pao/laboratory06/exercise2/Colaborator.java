package com.pao.laboratory06.exercise2;

public abstract class Colaborator implements IOperatiiRW {
    protected String nume;
    protected String prenume;
    protected double venitBrutLunar;
    public abstract double calculeazaVenitNetAnual();
    public abstract TipColaborator getTip();
}