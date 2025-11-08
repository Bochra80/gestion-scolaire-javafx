// MatiereDAO.java
package miniProjet.dao;

import miniProjet.model.Matiere;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatiereDAO {
    private Connection connection;
    
    public MatiereDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public boolean create(Matiere matiere) {
        String sql = "INSERT INTO matieres (nom, code, coefficient, volume_horaire) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, matiere.getNom());
            stmt.setString(2, matiere.getCode());
            stmt.setInt(3, matiere.getCoefficient());
            stmt.setInt(4, matiere.getVolumeHoraire());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur création matière: " + e.getMessage());
            return false;
        }
    }
    
    public List<Matiere> readAll() {
        List<Matiere> matieres = new ArrayList<>();
        String sql = "SELECT * FROM matieres ORDER BY nom";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                matieres.add(new Matiere(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("code"),
                    rs.getInt("coefficient"),
                    rs.getInt("volume_horaire")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture matières: " + e.getMessage());
        }
        return matieres;
    }
    
    public boolean update(Matiere matiere) {
        String sql = "UPDATE matieres SET nom=?, code=?, coefficient=?, volume_horaire=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, matiere.getNom());
            stmt.setString(2, matiere.getCode());
            stmt.setInt(3, matiere.getCoefficient());
            stmt.setInt(4, matiere.getVolumeHoraire());
            stmt.setInt(5, matiere.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour matière: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM matieres WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression matière: " + e.getMessage());
            return false;
        }
    }
}