package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // Folosește LinkedList<Tranzactie> ca structură internă.
        // Citește comenzi din stdin până la EOF:
        //
        //   ENQUEUE id suma data tip   → addLast  (niciun output)
        //   DEQUEUE                    → removeFirst sau "Coada goala."
        //                                format: "Procesat: [id] data tip: suma RON"
        //   PUSH id suma data tip      → addFirst  (niciun output)
        //   POP                        → removeFirst sau "Coada goala."
        //                                format: "Extras: [id] data tip: suma RON"
        //   REMOVE_DEBIT               → Iterator.remove() pe toate DEBIT
        //                                afișează "Eliminat N tranzactii DEBIT."
        //   REMOVE_BELOW threshold     → Iterator.remove() pe suma < threshold
        //                                afișează "Eliminat N tranzactii sub threshold RON."
        //   PRINT                      → afișează toate, câte una pe linie
        //   SIZE                       → "Dimensiune coada: N"
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-10 CREDIT: 500.00 RON

        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        LinkedList<Tranzactie> coada = new LinkedList<>();

        while (sc.hasNext()) {
            String comanda = sc.next();

            if (comanda.equals("ENQUEUE")) {
                int id = sc.nextInt();
                double suma = sc.nextDouble();
                String data = sc.next();
                TipTranzactie tip = TipTranzactie.valueOf(sc.next().toUpperCase());
                coada.addLast(new Tranzactie(id, suma, data, tip));

            } else if (comanda.equals("DEQUEUE")) {
                if (coada.isEmpty()) {
                    System.out.println("Coada goala.");
                } else {
                    Tranzactie t = coada.removeFirst();
                    System.out.println("Procesat: " + t);
                }

            } else if (comanda.equals("PUSH")) {
                int id = sc.nextInt();
                double suma = sc.nextDouble();
                String data = sc.next();
                TipTranzactie tip = TipTranzactie.valueOf(sc.next().toUpperCase());
                coada.addFirst(new Tranzactie(id, suma, data, tip));

            } else if (comanda.equals("POP")) {
                if (coada.isEmpty()) {
                    System.out.println("Coada goala.");
                } else {
                    Tranzactie t = coada.removeFirst();
                    System.out.println("Extras: " + t);
                }

            } else if (comanda.equals("REMOVE_DEBIT")) {
                Iterator<Tranzactie> it = coada.iterator();
                int count = 0;
                while (it.hasNext()) {
                    if (it.next().getTip() == TipTranzactie.DEBIT) {
                        it.remove();
                        count++;
                    }
                }
                System.out.println("Eliminat " + count + " tranzactii DEBIT.");

            } else if (comanda.equals("REMOVE_BELOW")) {
                double threshold = sc.nextDouble();
                Iterator<Tranzactie> it = coada.iterator();
                int count = 0;
                while (it.hasNext()) {
                    if (it.next().getSuma() < threshold) {
                        it.remove();
                        count++;
                    }
                }
                System.out.printf("Eliminat %d tranzactii sub %.2f RON.\n", count, threshold);

            } else if (comanda.equals("PRINT")) {
                for (Tranzactie t : coada) {
                    System.out.println(t);
                }

            } else if (comanda.equals("SIZE")) {
                System.out.println("Dimensiune coada: " + coada.size());
            }
        }
        sc.close();
    }
}