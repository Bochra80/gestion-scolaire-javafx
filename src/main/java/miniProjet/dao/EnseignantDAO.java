package miniProjet.dao;

import miniProjet.model.Enseignant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO {
    private Connection connection;
    
    public EnseignantDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public boolean create(Enseignant enseignant) {
        String sql = "INSERT INTO enseignants (nom, prenom, email, telephone, specialite) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, enseignant.getNom());
            stmt.setString(2, enseignant.getPrenom());
            stmt.setString(3, enseignant.getEmail());
            stmt.setString(4, enseignant.getTelephone());
            stmt.setString(5, enseignant.getSpecialite());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur création enseignant: " + e.getMessage());
            return false;
        }
    }
    
    public List<Enseignant> readAll() {
        List<Enseignant> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM enseignants ORDER BY nom, prenom";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                enseignants.add(new Enseignant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("specialite")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture enseignants: " + e.getMessage());
        }
        return enseignants;
    }
    
    public Enseignant readById(int id) {
        String sql = "SELECT * FROM enseignants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Enseignant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("specialite")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture enseignant: " + e.getMessage());
        }
        return null;
    }
    
    public boolean update(Enseignant enseignant) {
        String sql = "UPDATE enseignants SET nom=?, prenom=?, email=?, telephone=?, specialite=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, enseignant.getNom());
            stmt.setString(2, enseignant.getPrenom());
            stmt.setString(3, enseignant.getEmail());
            stmt.setString(4, enseignant.getTelephone());
            stmt.setString(5, enseignant.getSpecialite());
            stmt.setInt(6, enseignant.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour enseignant: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM enseignants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression enseignant: " + e.getMessage());
            return false;
        }
    }
    
    public List<Enseignant> search(String keyword) {
        List<Enseignant> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM enseignants WHERE nom LIKE ? OR prenom LIKE ? OR specialite LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                enseignants.add(new Enseignant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("specialite")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche: " + e.getMessage());
        }
        return enseignants;
    }
}