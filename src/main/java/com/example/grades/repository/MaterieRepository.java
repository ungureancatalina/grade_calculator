package com.example.grades.repository;

import com.example.grades.domain.Materie;
import com.example.grades.domain.Procentaje;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterieRepository {
    private final String url;
    private final String username;
    private final String password;

    public MaterieRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void addMaterie(int userId, String nume, int numarCredite) {
        String query = "INSERT INTO materii (nume, user_id, numar_credite) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nume);
            stmt.setInt(2, userId);
            stmt.setInt(3, numarCredite);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Materie> getMateriiByUser(int userId) {
        List<Materie> materii = new ArrayList<>();
        String query = "SELECT * FROM materii WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                materii.add(new Materie(rs.getInt("id"), rs.getString("nume"), rs.getInt("numar_credite")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materii;
    }

    public void removeMaterie(int userId, int materieId) {
        String query = "DELETE FROM materii WHERE id = ? AND user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, materieId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Materie findMaterieById(int materieId) {
        String query = "SELECT * FROM materii WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, materieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Materie(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getInt("numar_credite")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
