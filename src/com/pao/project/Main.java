package com.pao.project;

import com.pao.project.model.Autor;
import com.pao.project.model.Carte;
import com.pao.project.model.Cititor;
import com.pao.project.model.ISBN;
import com.pao.project.repository.CarteRepository;
import com.pao.project.repository.CititorRepository;
import com.pao.project.service.AutorService;
import com.pao.project.service.CarteService;
import com.pao.project.service.CititorService;
import com.pao.project.service.ImprumutService;

import java.sql.SQLException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        AutorService autorService = AutorService.getInstance();
        CarteService carteService = CarteService.getInstance();
        CititorService cititorService = CititorService.getInstance();
        ImprumutService imprumutService = ImprumutService.getInstance();

        System.out.println("=== 1. Adaugare Autori ===");
        Autor autor1 = new Autor("Mihai Eminescu");
        Autor autor2 = new Autor("Ion Creanga");
        autorService.adaugaAutor(autor1);
        autorService.adaugaAutor(autor2);

        System.out.println("\n=== 2. Adaugare Carti ===");
        Carte carte1 = new Carte("Poezii", autor1, new ISBN("111-222"));
        carte1.setAutorId(autor1.getId());
        
        Carte carte2 = new Carte("Amintiri din Copilarie", autor2, new ISBN("333-444"));
        carte2.setAutorId(autor2.getId());

        carteService.adaugaCarte(carte1);
        carteService.adaugaCarte(carte2);

        System.out.println("\n=== 3. Adaugare Cititori ===");
        Cititor cititor1 = new Cititor(1, "Ana Maria");
        Cititor cititor2 = new Cititor(2, "Andrei Popescu");
        cititorService.adaugaCititor(cititor1);
        cititorService.adaugaCititor(cititor2);

        System.out.println("\n=== 4. TRANZACTIE: Imprumutare Carte ===");
        try {
            imprumutService.imprumuta(carte1, cititor1);
            System.out.println("Cartea " + carte1.getTitlu() + " a fost imprumutata cu succes!");
        } catch (Exception e) {
            System.out.println("Eroare la imprumut: " + e.getMessage());
        }

        System.out.println("\n=== 5. Afisare Imprumuturi Active (JOIN 3) ===");
        imprumutService.afiseazaImprumuturiActive();

        System.out.println("\n=== 6. Afisare Carti + Autori (JOIN 2) ===");
        CarteRepository carteRepo = new CarteRepository();
        for(String c : carteRepo.getCartiCuNumeAutori()) {
            System.out.println(c);
        }

        System.out.println("\n=== 7. Afisare Statistici Cititori (JOIN 1) ===");
        CititorRepository cititorRepo = new CititorRepository();
        for(Map.Entry<String, Integer> entry : cititorRepo.getCititoriCuNumarImprumuturi().entrySet()) {
            System.out.println("Cititor: " + entry.getKey() + " | Imprumuturi active: " + entry.getValue());
        }

        System.out.println("\n=== 8. Returnare Carte ===");
        imprumutService.returneaza(carte1);
        System.out.println("Cartea a fost returnata!");
        System.out.println("Status imprumuturi acum:");
        imprumutService.afiseazaImprumuturiActive(); // Va fi gol

        System.out.println("\n=== 9 & 10. Stergere (Clean-up) ===");
        carteService.stergeCarte(carte2.getId());
        cititorService.stergeCititor(cititor2.getId());
        System.out.println("Stergerile au fost logate in Audit.");
        
        System.out.println("\nProiect Etapa 2 finalizat cu succes! Verifica fisierul audit.csv.");
    }
}