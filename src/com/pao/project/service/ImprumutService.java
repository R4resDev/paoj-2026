package com.pao.project.service;

import com.pao.project.exception.CarteNedisponibilaException;
import com.pao.project.model.Carte;
import com.pao.project.model.Cititor;
import com.pao.project.repository.ImprumutRepository;
import com.pao.project.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ImprumutService {
    private static ImprumutService instance;
    private final ImprumutRepository imprumutRepository = new ImprumutRepository();

    private ImprumutService() {}

    public static ImprumutService getInstance() {
        if (instance == null) {
            instance = new ImprumutService();
        }
        return instance;
    }

    public void imprumuta(Carte carte, Cititor cititor) throws SQLException {
        if (!carte.isDisponibila()) {
            throw new CarteNedisponibilaException("Cartea nu este disponibila");
        }

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            String insertSql = "INSERT INTO imprumuturi (carte_id, cititor_id, activ) VALUES (?, ?, true)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, carte.getId());
                ps.setInt(2, cititor.getId());
                ps.executeUpdate();
            }

            String updateSql = "UPDATE carti SET disponibila = false WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, carte.getId());
                ps.executeUpdate();
            }

            conn.commit(); // Commit tranzactie
            carte.setDisponibila(false);
            AuditService.getInstance().logAction("imprumuta_carte"); 

        } catch (SQLException e) {
            conn.rollback(); // Rollback in caz de eroare
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void returneaza(Carte carte) {
        if (carte != null) {
            try {
                Connection conn = DatabaseConnection.getInstance().getConnection();
                
                String updateSql = "UPDATE carti SET disponibila = true WHERE id = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setInt(1, carte.getId());
                    ps.executeUpdate();
                }
                
                String updateImprumutSql = "UPDATE imprumuturi SET activ = false WHERE carte_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateImprumutSql)) {
                    ps.setInt(1, carte.getId());
                    ps.executeUpdate();
                }

                carte.setDisponibila(true);
                AuditService.getInstance().logAction("returneaza_carte");
            } catch (SQLException e) {
                System.err.println("Eroare la returnare DB: " + e.getMessage());
            }
        }
    }

    public void afiseazaImprumuturiActive() {
        AuditService.getInstance().logAction("afiseaza_imprumuturi_active");
        try {
            List<String> detalii = imprumutRepository.getDetaliiImprumuturiActive();
            for (String detaliu : detalii) {
                System.out.println(detaliu);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la extragere JOIN imprumuturi: " + e.getMessage());
        }
    }
}