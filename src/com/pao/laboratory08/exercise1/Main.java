package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează

        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 4) {
                    continue;
                }

                String nume = parts[0].trim();
                int varsta = Integer.parseInt(parts[1].trim());
                String oras = parts[2].trim();
                String strada = parts[3].trim();

                Adresa adresa = new Adresa(oras, strada);
                Student student = new Student(nume, varsta, adresa);

                studenti.add(student);
            }
        }

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        String[] tokens = input.split(" ", 2);

        String command = tokens[0];

        if (command.equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
        } else {
            String nume = tokens[1];

            Student target = null;
            for (Student s : studenti) {
                if (s.getNume().equals(nume)) {
                    target = s;
                    break;
                }
            }

            if (target == null) return;

            if (command.equals("SHALLOW")) {
                Student clona = target.shallowClone();
                clona.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + target);
                System.out.println("Clona: " + clona);

            } else if (command.equals("DEEP")) {
                Student clona = target.deepClone();
                clona.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + target);
                System.out.println("Clona: " + clona);
            }
        }
    }
}