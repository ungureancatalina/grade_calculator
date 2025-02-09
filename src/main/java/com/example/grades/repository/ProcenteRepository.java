package com.example.grades.repository;

import com.example.grades.domain.Materie;
import com.example.grades.domain.Procentaje;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class ProcenteRepository {
    private final String url;
    private final String username;
    private final String password;

    public ProcenteRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public boolean addProcentaj(int materieId, double procentaj, double nota) {
        String query = "INSERT INTO procentaje (materie_id, procent, nota) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, materieId);
            stmt.setDouble(2, procentaj);
            stmt.setDouble(3, nota);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeProcentaj(int materieId, double procentaj, double nota) {
        String query = "DELETE FROM procentaje WHERE materie_id = ? AND procent = ? AND nota = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, materieId);
            stmt.setDouble(2, procentaj);
            stmt.setDouble(3, nota);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Procentaje> getProcentajeByMaterie(int materieId) {
        List<Procentaje> procentaje = new ArrayList<>();
        String query = "SELECT procent, nota FROM procentaje WHERE materie_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, materieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    procentaje.add(new Procentaje(rs.getDouble("procent"), rs.getDouble("nota")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("📊 Procente încărcate pentru materie " + materieId + ": " + procentaje.size());
        return procentaje;
    }


}
