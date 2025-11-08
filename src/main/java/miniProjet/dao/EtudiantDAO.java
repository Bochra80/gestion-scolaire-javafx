package miniProjet.dao;

import miniProjet.model.Etudiant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    private Connection connection;
    
    public EtudiantDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    // Créer un étudiant
    public boolean create(Etudiant etudiant) {
        String sql = "INSERT INTO etudiants (nom, prenom, date_naissance, email, telephone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, etudiant.getNom());
            stmt.setString(2, etudiant.getPrenom());
            stmt.setString(3, etudiant.getDateNaissance());
            stmt.setString(4, etudiant.getEmail());
            stmt.setString(5, etudiant.getTelephone());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'étudiant: " + e.getMessage());
            return false;
        }
    }
    
    // Lire tous les étudiants
    public List<Etudiant> readAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiants ORDER BY nom, prenom";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Etudiant etudiant = new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("date_naissance"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des étudiants: " + e.getMessage());
        }
        return etudiants;
    }
    
    // Lire un étudiant par ID
    public Etudiant readById(int id) {
        String sql = "SELECT * FROM etudiants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("date_naissance"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture de l'étudiant: " + e.getMessage());
        }
        return null;
    }
    
    // Mettre à jour un étudiant
    public boolean update(Etudiant etudiant) {
        String sql = "UPDATE etudiants SET nom=?, prenom=?, date_naissance=?, email=?, telephone=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, etudiant.getNom());
            stmt.setString(2, etudiant.getPrenom());
            stmt.setString(3, etudiant.getDateNaissance());
            stmt.setString(4, etudiant.getEmail());
            stmt.setString(5, etudiant.getTelephone());
            stmt.setInt(6, etudiant.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'étudiant: " + e.getMessage());
            return false;
        }
    }
    
    // Supprimer un étudiant
    public boolean delete(int id) {
        String sql = "DELETE FROM etudiants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'étudiant: " + e.getMessage());
            return false;
        }
    }
    
    // Rechercher des étudiants
    public List<Etudiant> search(String keyword) {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiants WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Etudiant etudiant = new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("date_naissance"),
                    rs.getString("email"),
                    rs.getString("telephone")
                );
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche: " + e.getMessage());
        }
        return etudiants;
    }
}