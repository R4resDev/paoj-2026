package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";
    private static final String OUTPUT_PATH = "src/com/pao/laboratory08/exercise2/rezultate.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește pragul de vârstă din stdin cu Scanner
        // 3. Filtrează studenții cu varsta >= prag
        // 4. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        // 5. Afișează sumarul la consolă
        List<Student> studenti = citesteStudentiDinFisier();

        Scanner scanner = new Scanner(System.in);
        int pragVarsta = scanner.nextInt();

        List<Student> filtrati = new ArrayList<>();
        for (Student student : studenti) {
            if (student.getVarsta() >= pragVarsta) {
                filtrati.add(student);
            }
        }

        try (BufferedWriter fout = new BufferedWriter(new FileWriter(OUTPUT_PATH))) {
            for (Student student : filtrati) {
                fout.write(student.toString());
                fout.newLine();
            }
        }

        System.out.println("Filtru: varsta >= " + pragVarsta);
        System.out.println("Rezultate: " + filtrati.size() + " studenti");

        for (Student student : filtrati) {
            System.out.println(student);
        }

        System.out.println("Scris in: " + OUTPUT_PATH);
    }

    private static List<Student> citesteStudentiDinFisier() throws Exception {
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

        return studenti;
    }
}