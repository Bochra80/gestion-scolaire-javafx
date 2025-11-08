// GroupeDAO.java
package miniProjet.dao;

import miniProjet.model.Groupe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeDAO {
    private Connection connection;
    
    public GroupeDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public boolean create(Groupe groupe) {
        String sql = "INSERT INTO groupes (nom, niveau, capacite) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, groupe.getNom());
            stmt.setString(2, groupe.getNiveau());
            stmt.setInt(3, groupe.getCapacite());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur création groupe: " + e.getMessage());
            return false;
        }
    }
    
    public List<Groupe> readAll() {
        List<Groupe> groupes = new ArrayList<>();
        String sql = "SELECT * FROM groupes ORDER BY nom";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                groupes.add(new Groupe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("niveau"),
                    rs.getInt("capacite")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture groupes: " + e.getMessage());
        }
        return groupes;
    }
    
    public boolean update(Groupe groupe) {
        String sql = "UPDATE groupes SET nom=?, niveau=?, capacite=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, groupe.getNom());
            stmt.setString(2, groupe.getNiveau());
            stmt.setInt(3, groupe.getCapacite());
            stmt.setInt(4, groupe.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour groupe: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM groupes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur suppression groupe: " + e.getMessage());
            return false;
        }
    }
}