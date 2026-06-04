package com.pao.project.repository;

import com.pao.project.model.Carte;
import com.pao.project.model.ISBN;
import com.pao.project.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class CarteRepository implements Repository<Carte, Integer> {
    @Override
    public void save(Carte entity) throws SQLException {
        String sql = "INSERT INTO carti (titlu, isbn, disponibila, autor_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getTitlu());
            ps.setString(2, entity.getIsbn().getCod());
            ps.setBoolean(3, entity.isDisponibila());
            ps.setInt(4, entity.getAutorId());
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Optional<Carte> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM carti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carte c = new Carte(rs.getString("titlu"), null, new ISBN(rs.getString("isbn")));
                    c.setId(rs.getInt("id"));
                    c.setDisponibila(rs.getBoolean("disponibila"));
                    c.setAutorId(rs.getInt("autor_id"));
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Carte> findAll() throws SQLException {
        List<Carte> list = new ArrayList<>();
        String sql = "SELECT * FROM carti";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Carte c = new Carte(rs.getString("titlu"), null, new ISBN(rs.getString("isbn")));
                c.setId(rs.getInt("id"));
                c.setDisponibila(rs.getBoolean("disponibila"));
                c.setAutorId(rs.getInt("autor_id"));
                list.add(c);
            }
        }
        return list;
    }

    @Override
    public void update(Carte entity) throws SQLException {
        String sql = "UPDATE carti SET titlu = ?, disponibila = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getTitlu());
            ps.setBoolean(2, entity.isDisponibila());
            ps.setInt(3, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM carti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<String> getCartiCuNumeAutori() throws SQLException {
        List<String> rezultate = new ArrayList<>();
        String sql = "SELECT c.titlu, a.nume AS autor FROM carti c JOIN autori a ON c.autor_id = a.id";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rezultate.add("Cartea: " + rs.getString("titlu") + " | Autor: " + rs.getString("autor"));
            }
        }
        return rezultate;
    }
}