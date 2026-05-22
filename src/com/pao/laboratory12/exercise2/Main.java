package com.pao.laboratory12.exercise2;

import com.pao.laboratory12.exercise1.model.*;
import com.pao.laboratory12.exercise1.repository.*;
import com.pao.laboratory12.exercise2.service.*;
import com.pao.laboratory12.exercise1.util.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/pao/laboratory12/resources/schema.sql"));
             Statement stmt = conn.createStatement()) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sqlBuilder.append(line).append("\n");
            for (String query : sqlBuilder.toString().split(";")) {
                if (!query.trim().isEmpty()) stmt.execute(query.trim());
            }
        }

        AuditService audit = AuditService.getInstance();
        AuthorRepository authorRepo = new AuthorRepository();
        BookRepository bookRepo = new BookRepository();
        ReaderRepository readerRepo = new ReaderRepository();
        LibraryService libraryService = LibraryService.getInstance();

        System.out.println("=== Demo Lab 12 Complet ===");

        Author author = new Author("Gabriel Garcia Marquez", "CO");
        authorRepo.save(author);
        audit.log("add_author");
        System.out.println("1. Autor adaugat: " + author);

        Book book1 = new Book("100 de ani de singuratate", author.getId());
        bookRepo.save(book1);
        audit.log("add_book");
        System.out.println("2. Carte adaugata: " + book1);

        Reader reader = new Reader("Ion Popescu", "ion.popescu@email.com");
        readerRepo.save(reader);
        audit.log("add_reader");
        System.out.println("3. Cititor adaugat: " + reader);

        List<Book> allBooks = bookRepo.findAll();
        audit.log("list_books");
        System.out.println("4. Toate cartile: " + allBooks);

        Book foundBook = bookRepo.findById(book1.getId()).orElse(null);
        audit.log("find_book_by_id");
        System.out.println("5. Carte gasita: " + foundBook);

        book1.setTitle("100 de ani de singuratate (Ed. 2)");
        bookRepo.update(book1);
        audit.log("update_book");
        System.out.println("6. Carte actualizata: " + book1.getTitle());

        long loanId = libraryService.borrowBook(reader.getId(), book1.getId());
        audit.log("borrow_book");
        System.out.println("7. Imprumut creat cu ID: " + loanId);

        libraryService.returnBook(loanId);
        audit.log("return_book");
        System.out.println("8. Carte returnata cu succes.");

        List<String> topBooks = libraryService.getTopBorrowedBooksWithAuthor();
        audit.log("report_top_books");
        System.out.println("9. Top carti imprumutate: " + topBooks);

        readerRepo.delete(reader.getId());
        audit.log("delete_reader");
        System.out.println("10. Cititor sters cu ID: " + reader.getId());

        DatabaseConnection.getInstance().close();
        System.out.println("=== Gata! Verifica fisierul audit.csv ===");
    }
}