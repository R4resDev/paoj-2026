package com.pao.project;

import com.pao.project.exception.CarteNedisponibilaException;
import com.pao.project.exception.CititorNegasitException;
import com.pao.project.model.*;
import com.pao.project.service.CarteService;
import com.pao.project.service.CititorService;
import com.pao.project.service.ImprumutService;

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca("Biblioteca Centrala");
        CarteService carteService = CarteService.getInstance();
        CititorService cititorService = CititorService.getInstance();
        ImprumutService imprumutService = ImprumutService.getInstance();

        Bibliotecar bibliotecar = new Bibliotecar("Maria Popescu", 4500, "Inregistrare");
        System.out.println(bibliotecar);

        Autor autor1 = new Autor("Mihai Eminescu");
        Autor autor2 = new Autor("Ion Creanga");

        Sectiune sectiunePoezie = new Sectiune("Poezie");
        Sectiune sectiuneProza = new Sectiune("Proza");

        Carte carte1 = new Carte("Poezii", autor1, new ISBN("ISBN-001"));
        Carte carte2 = new Carte("Luceafarul", autor1, new ISBN("ISBN-002"));
        Carte carte3 = new Carte("Amintiri din copilarie", autor2, new ISBN("ISBN-003"));

        carteService.adaugaCarte(carte1);
        carteService.adaugaCarte(carte2);
        carteService.adaugaCarte(carte3);

        biblioteca.adaugaCarte(carte1);
        biblioteca.adaugaCarte(carte2);
        biblioteca.adaugaCarte(carte3);

        Carte carteTemp = new Carte("Povestea lui Harap-Alb", autor2, new ISBN("ISBN-999"));
        carteService.adaugaCarte(carteTemp);
        carteService.stergeCarte("Povestea lui Harap-Alb");

        System.out.println(carteService.cautaCarte("Poezii"));

        for (Carte carte : carteService.listeazaCarti()) {
            System.out.println(carte);
        }

        Cititor cititor1 = new Cititor(1, "Ana");
        Cititor cititor2 = new Cititor(2, "Mihai");
        cititorService.adaugaCititor(cititor1);
        cititorService.adaugaCititor(cititor2);

        biblioteca.adaugaCititor(cititor1);
        biblioteca.adaugaCititor(cititor2);

        Cititor cititorTemp = new Cititor(99, "Temp");
        cititorService.adaugaCititor(cititorTemp);
        cititorService.stergeCititor(99);

        try {
            cititorService.cautaDupaId(99);
        } catch (CititorNegasitException e) {
            System.out.println(e.getMessage());
        }

        imprumutService.imprumuta(carte1, cititor1);
        biblioteca.adaugaImprumut(new Imprumut(carte1, cititor1));

        imprumutService.returneaza(carte1);

        for (Imprumut imprumut : imprumutService.imprumuturiPentruCititor(1)) {
            System.out.println(imprumut);
        }

        System.out.println(carte1.getTitlu() + " disponibil? " + carte1.isDisponibila());

        try {
            imprumutService.imprumuta(carte2, cititor1);
            imprumutService.imprumuta(carte2, cititor2);
        } catch (CarteNedisponibilaException e) {
            System.out.println(e.getMessage());
        }

        sectiunePoezie.adaugaCarte(carte1);
        sectiunePoezie.adaugaCarte(carte2);
        sectiuneProza.adaugaCarte(carte3);

        biblioteca.adaugaSectiune(sectiunePoezie);
        biblioteca.adaugaSectiune(sectiuneProza);

        System.out.println(biblioteca);
    }
}