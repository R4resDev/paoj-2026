package com.pao.laboratory12.exercise1;

import com.pao.laboratory12.exercise1.model.Author;
import com.pao.laboratory12.exercise1.repository.AuthorRepository;
import com.pao.laboratory12.exercise1.util.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (BufferedReader br = new BufferedReader(new FileReader("src/com/pao/laboratory12/resources/schema.sql"));
             Statement stmt = conn.createStatement()) {
            
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
            
            for (String query : sqlBuilder.toString().split(";")) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query.trim());
                }
            }
        }
        System.out.println("Tabelele au fost create cu succes.");

        AuthorRepository repo = new AuthorRepository();
        
        Author autorNou = new Author("Gabriel Garcia Marquez", "CO");
        repo.save(autorNou);
        System.out.println("Autor salvat cu ID: " + autorNou.getId());

        System.out.println("Lista autorilor:");
        for (Author a : repo.findAll()) {
            System.out.println(" - " + a);
        }

        DatabaseConnection.getInstance().close();
    }
}