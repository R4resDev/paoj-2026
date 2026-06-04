package com.pao.project.repository;

import com.pao.project.model.Carte;
import com.pao.project.model.Cititor;
import com.pao.project.model.Imprumut;
import com.pao.project.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImprumutRepository implements Repository<Imprumut, Integer> {

    @Override
    public void save(Imprumut entity) throws SQLException {}

    @Override
    public Optional<Imprumut> findById(Integer id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void update(Imprumut entity) throws SQLException {}

    @Override
    public void delete(Integer id) throws SQLException {}

    @Override
    public List<Imprumut> findAll() throws SQLException {
        List<Imprumut> list = new ArrayList<>();
        String sql = "SELECT * FROM imprumuturi";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Carte cMock = new Carte("Mock", null, null); cMock.setId(rs.getInt("carte_id"));
                Cititor citMock = new Cititor(rs.getInt("cititor_id"), "Mock");
                Imprumut i = new Imprumut(cMock, citMock);
                i.setId(rs.getInt("id"));
                i.setActiv(rs.getBoolean("activ"));
                list.add(i);
            }
        }
        return list;
    }

    public List<String> getDetaliiImprumuturiActive() throws SQLException {
        List<String> rezultate = new ArrayList<>();
        String sql = "SELECT i.id, c.titlu, r.nume AS cititor FROM imprumuturi i " +
                     "JOIN carti c ON i.carte_id = c.id " +
                     "JOIN cititori r ON i.cititor_id = r.id " +
                     "WHERE i.activ = true";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rezultate.add("Imprumut #" + rs.getInt("id") + " -> Cartea: '" +  rs.getString("titlu") + "' la cititorul: " + rs.getString("cititor"));
            }
        }
        return rezultate;
    }
}