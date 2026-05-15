package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static class TranzactieDemo {
        int id;
        double suma;
        String data;
        TipTranzactie tip;
        String contSursa;

        public TranzactieDemo(int id, double suma, String data, TipTranzactie tip, String contSursa) {
            this.id = id;
            this.suma = suma;
            this.data = data;
            this.tip = tip;
            this.contSursa = contSursa;
        }

        public int getId() { return id; }
        public double getSuma() { return suma; }
        public String getData() { return data; }
        public TipTranzactie getTip() { return tip; }
        public String getContSursa() { return contSursa; }

        @Override
        public String toString() {
            return String.format("[%d] %s %s: %.2f RON | Sursa: %s", id, data, tip, suma, contSursa);
        }
    }

    public static void main(String[] args) {
        List<TranzactieDemo> tranzactii = Arrays.asList(
            new TranzactieDemo(1, 1500.00, "2026-01-15", TipTranzactie.CREDIT, "RO01AAA"),
            new TranzactieDemo(2, 750.50, "2026-01-22", TipTranzactie.DEBIT, "RO02BBB"),
            new TranzactieDemo(3, 200.00, "2026-02-05", TipTranzactie.CREDIT, "RO01AAA"),
            new TranzactieDemo(4, 1200.00, "2026-02-18", TipTranzactie.DEBIT, "RO03CCC"),
            new TranzactieDemo(5, 500.00, "2026-03-10", TipTranzactie.CREDIT, "RO02BBB"),
            new TranzactieDemo(6, 300.00, "2026-03-22", TipTranzactie.DEBIT, "RO01AAA"),
            new TranzactieDemo(7, 450.00, "2026-01-05", TipTranzactie.CREDIT, "RO04DDD"),
            new TranzactieDemo(8, 90.00, "2026-02-25", TipTranzactie.DEBIT, "RO02BBB"),
            new TranzactieDemo(9, 3000.00, "2026-03-15", TipTranzactie.CREDIT, "RO03CCC"),
            new TranzactieDemo(10, 150.00, "2026-03-28", TipTranzactie.DEBIT, "RO04DDD")
        );

        tranzactii.stream().filter(t -> t.getTip() == TipTranzactie.CREDIT).forEach(System.out::println);

        double total = tranzactii.stream().mapToDouble(TranzactieDemo::getSuma).sum();
        System.out.printf("Total procesat: %.2f RON\n", total);

        Map<String, Double> perLuna = tranzactii.stream().collect(Collectors.groupingBy(t -> t.getData().substring(0, 7), TreeMap::new, Collectors.summingDouble(TranzactieDemo::getSuma)));
        perLuna.forEach((luna, suma) -> System.out.printf("%s: %.2f RON\n", luna, suma));

        tranzactii.stream().sorted(Comparator.comparingDouble(TranzactieDemo::getSuma).reversed()).limit(3).forEach(System.out::println);

        List<String> conturiUnice = tranzactii.stream().map(TranzactieDemo::getContSursa).distinct().collect(Collectors.toList());
        System.out.println("Conturi sursa unice: " + conturiUnice);

        OptionalDouble medie = tranzactii.stream().mapToDouble(TranzactieDemo::getSuma).average();
        System.out.printf("Suma medie: %.2f RON\n", medie.orElse(0.0));

        Map<String, List<TranzactieDemo>> extrase = tranzactii.stream().collect(Collectors.groupingBy(t -> t.getData().substring(0, 7), TreeMap::new, Collectors.toList()));
        
        extrase.forEach((luna, listaDinLuna) -> {
            double totalLuna = listaDinLuna.stream().mapToDouble(TranzactieDemo::getSuma).sum();
            System.out.printf("EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON\n", luna, listaDinLuna.size(), totalLuna);
        });
    }
}