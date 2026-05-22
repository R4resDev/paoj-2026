package com.pao.laboratory12.exercise2.service;

import com.pao.laboratory12.exercise1.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private static LibraryService instance;

    private LibraryService() {}

    public static LibraryService getInstance() {
        if (instance == null) instance = new LibraryService();
        return instance;
    }

    private Connection getConn() throws Exception {
        return DatabaseConnection.getInstance().getConnection();
    }

    public long borrowBook(long readerId, long bookId) throws Exception {
        Connection conn = getConn();
        conn.setAutoCommit(false); 
        try {
            try (PreparedStatement ps = conn.prepareStatement("SELECT available FROM book WHERE id = ?")) {
                ps.setLong(1, bookId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getInt("available") == 0) {
                    throw new SQLException("Cartea nu este disponibila!");
                }
            }

            long loanId;
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO loan (book_id, reader_id, loan_date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, bookId);
                ps.setLong(2, readerId);
                ps.setString(3, LocalDate.now().toString());
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                loanId = keys.getLong(1);
            }

            try (PreparedStatement ps = conn.prepareStatement("UPDATE book SET available = 0 WHERE id = ?")) {
                ps.setLong(1, bookId);
                ps.executeUpdate();
            }

            conn.commit(); 
            return loanId;
        } catch (Exception e) {
            conn.rollback(); 
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void returnBook(long loanId) throws Exception {
        Connection conn = getConn();
        conn.setAutoCommit(false);
        try {
            long bookId;
            try (PreparedStatement ps = conn.prepareStatement("SELECT book_id FROM loan WHERE id = ?")) {
                ps.setLong(1, loanId);
                ResultSet rs = ps.executeQuery();
                rs.next();
                bookId = rs.getLong("book_id");
            }

            try (PreparedStatement ps = conn.prepareStatement("UPDATE loan SET return_date = ? WHERE id = ?")) {
                ps.setString(1, LocalDate.now().toString());
                ps.setLong(2, loanId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement("UPDATE book SET available = 1 WHERE id = ?")) {
                ps.setLong(1, bookId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<String> getActiveLoansWithDetails() throws Exception {
        String sql = "SELECT l.id, b.title, r.name FROM loan l JOIN book b ON l.book_id = b.id JOIN reader r ON l.reader_id = r.id WHERE l.return_date IS NULL";
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) results.add("Loan#" + rs.getLong("id") + " | " + rs.getString("title") + " -> " + rs.getString("name"));
        }
        return results;
    }

    public List<String> getTopBorrowedBooksWithAuthor() throws Exception {
        String sql = "SELECT b.title, a.name, COUNT(l.id) AS total FROM book b JOIN author a ON b.author_id = a.id LEFT JOIN loan l ON l.book_id = b.id GROUP BY b.id, b.title, a.name ORDER BY total DESC LIMIT 10";
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) results.add(rs.getString("title") + " de " + rs.getString("name") + " — " + rs.getLong("total") + " imprumuturi");
        }
        return results;
    }

    public List<String> getLoansCountPerReader() throws Exception {
        String sql = "SELECT r.name, COUNT(l.id) AS total FROM reader r LEFT JOIN loan l ON l.reader_id = r.id GROUP BY r.id, r.name ORDER BY total DESC";
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) results.add(rs.getString("name") + ": " + rs.getLong("total") + " imprumuturi");
        }
        return results;
    }
}