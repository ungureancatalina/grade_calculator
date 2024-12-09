package com.example.grades.repository;

import com.example.grades.domain.Materie;
import com.example.grades.domain.MaterieValidator;
import com.example.grades.domain.Procentaje;
import com.example.grades.domain.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MaterieRepository {
    MaterieValidator validator;
    private String url;
    private String username;
    private String password;

    public MaterieRepository(MaterieValidator validator,String url, String username, String password) {
        this.validator =new MaterieValidator();;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void addMaterie(Materie materie) {
        validator.validate(materie);
        String query = "INSERT INTO materii (nume) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, materie.getNume());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        materie.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeMaterie(Materie materie) {
        String query = "DELETE FROM materii WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, materie.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Materie findMaterieById(int id) {
        String query = "SELECT * FROM materii WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Materie(rs.getInt("id"), rs.getString("nume"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Materie findMaterieByNume(String nume) {
        String query = "SELECT * FROM materii WHERE nume = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nume);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Materie(rs.getInt("id"), rs.getString("nume"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Materie> getAllMaterii() {
        List<Materie> materii = new ArrayList<>();
        String query = "SELECT * FROM materii";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materii.add(new Materie(rs.getInt("id"), rs.getString("nume")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materii;
    }

    public boolean addProcentaj(int materieId, Procentaje procentaj) {
        String query = "INSERT INTO procentaje (materie_id, procent, nota) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, materieId);
            stmt.setDouble(2, procentaj.getProcent());
            stmt.setDouble(3, procentaj.getNota());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeProcentaj(int materieId, double procentaj, double nota) {
        String query = "DELETE FROM procentaje WHERE materie_id = ? AND procent = ? AND nota = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, materieId);
            stmt.setDouble(2, procentaj);
            stmt.setDouble(3, nota);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
                    double procent = rs.getDouble("procent");
                    double nota = rs.getDouble("nota");
                    procentaje.add(new Procentaje(procent, nota));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return procentaje;
    }
}


