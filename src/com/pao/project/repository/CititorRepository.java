package com.pao.project.repository;

import com.pao.project.model.Cititor;
import com.pao.project.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class CititorRepository implements Repository<Cititor, Integer> {

    @Override
    public void save(Cititor entity) throws SQLException {
        String sql = "INSERT INTO cititori (id, nume) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getNume());
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Cititor> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM cititori WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Cititor(rs.getInt("id"), rs.getString("nume")));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Cititor> findAll() throws SQLException {
        List<Cititor> list = new ArrayList<>();
        String sql = "SELECT * FROM cititori";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Cititor(rs.getInt("id"), rs.getString("nume")));
            }
        }
        return list;
    }

    @Override
    public void update(Cititor entity) throws SQLException {
        String sql = "UPDATE cititori SET nume = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getNume());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM cititori WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Map<String, Integer> getCititoriCuNumarImprumuturi() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT c.nume, COUNT(i.id) AS numar FROM cititori c " +
                     "JOIN imprumuturi i ON c.id = i.cititor_id WHERE i.activ = true GROUP BY c.nume";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                stats.put(rs.getString("nume"), rs.getInt("numar"));
            }
        }
        return stats;
    }
}