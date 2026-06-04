package com.pao.project.repository;

import com.pao.project.model.Autor;
import com.pao.project.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorRepository implements Repository<Autor, Integer> {
    @Override
    public void save(Autor entity) throws SQLException {
        String sql = "INSERT INTO autori (nume) VALUES (?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getNume());
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Optional<Autor> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM autori WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Autor a = new Autor(rs.getString("nume"));
                    a.setId(rs.getInt("id"));
                    return Optional.of(a);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Autor> findAll() throws SQLException {
        List<Autor> autori = new ArrayList<>();
        String sql = "SELECT * FROM autori";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Autor a = new Autor(rs.getString("nume"));
                a.setId(rs.getInt("id"));
                autori.add(a);
            }
        }
        return autori;
    }

    @Override
    public void update(Autor entity) throws SQLException {
        String sql = "UPDATE autori SET nume = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getNume());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM autori WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}