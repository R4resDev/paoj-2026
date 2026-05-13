package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String OUTPUT_FILE = "src/com/pao/laboratory09/exercise1/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Tranzactie> listaTranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next().toUpperCase());

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            
            t.note = "procesat";
            listaTranzactii.add(t);
        }

        try (ObjectOutputStream g = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            g.writeObject(listaTranzactii);
        } catch (IOException e) {
            System.err.println("Eroare la scrierea fișierului: " + e.getMessage());
        }

        List<Tranzactie> tranzactiiDeserializate = new ArrayList<>();
        try (ObjectInputStream g = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            tranzactiiDeserializate = (List<Tranzactie>) g.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "LIST":
                    tranzactiiDeserializate.forEach(System.out::println);
                    break;

                case "FILTER":
                    if (scanner.hasNext()) {
                        String prefix = scanner.next();
                        List<Tranzactie> filtrate = tranzactiiDeserializate.stream().filter(t -> t.data.startsWith(prefix)).collect(Collectors.toList());

                        if (filtrate.isEmpty()) {
                            System.out.println("Niciun rezultat.");
                        } else {
                            filtrate.forEach(System.out::println);
                        }
                    }
                    break;

                case "NOTE":
                    if (scanner.hasNextInt()) {
                        int searchId = scanner.nextInt();
                        Optional<Tranzactie> gasit = tranzactiiDeserializate.stream().filter(t -> t.id == searchId).findFirst();

                        if (gasit.isPresent()) {
                            System.out.println("NOTE[" + searchId + "]: " + gasit.get().note);
                        } else {
                            System.out.println("NOTE[" + searchId + "]: not found");
                        }
                    }
                    break;
            }
        }
        scanner.close();
    }
}