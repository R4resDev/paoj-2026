# Sistem de Gestiune Bibliotecă - Etapa I

## 1. Definirea sistemului

### 1.1 — Lista cu acțiuni sau interogări posibile în sistem

1.  **Adăugare carte:** Înregistrarea unui nou volum în baza de date a bibliotecii prin `CarteService`.
2.  **Înregistrare cititor:** Crearea unui profil nou pentru un utilizator în `CititorService`.
3.  **Creare împrumut:** Asocierea unei cărți cu un cititor pentru o perioadă determinată prin `ImprumutService`.
4.  **Returnare carte:** Finalizarea unui împrumut activ și actualizarea stării de disponibilitate.
5.  **Căutare după ISBN:** Identificarea unei cărți folosind obiectul imutabil `ISBN`.
6.  **Listare cărți pe secțiune:** Afișarea tuturor titlurilor care aparțin unei anumite `Sectiune`.
7.  **Afișare istoric cititor:** Vizualizarea tuturor împrumuturilor efectuate de un anumit `Cititor`.
8.  **Verificare disponibilitate:** Interogarea sistemului pentru a vedea dacă o carte poate fi împrumutată (aruncă `CarteNedisponibilaException` dacă este deja dată).
9.  **Gestionare personal:** Adăugarea sau vizualizarea datelor pentru un `Bibliotecar`.
10. **Eliminare cititor:** Ștergerea unui cont de cititor din sistem (doar dacă nu are împrumuturi active).

### 1.2 — Lista cu tipuri de obiecte din domeniu

1.  **Biblioteca**: Clasa principală care centralizează listele de cărți, cititori și angajați.
2.  **Carte**: Conține detalii despre titlu, autor și disponibilitate.
3.  **Autor**: Reține numele și biografia scriitorului.
4.  **Cititor**: Extinde `Persoana` și gestionează datele de contact ale membrilor.
5.  **ISBN**: Clasă imutabilă utilizată ca identificator unic pentru cărți.
6.  **Imprumut**: Înregistrează legătura dintre un `Cititor`, o `Carte` și data tranzacției.
7.  **Sectiune**: Definește categoriile tematice (ex: Beletristică, Știință).
8.  **Bibliotecar**: Extinde `AngajatBiblioteca` (care la rândul său extinde `Persoana`), reprezentând personalul administrativ.
9.  **Persoana**: Clasă abstractă care definește atributele comune (nume, prenume).
10. **AngajatBiblioteca**: Clasă intermediară în ierarhia de moștenire pentru personalul instituției.
