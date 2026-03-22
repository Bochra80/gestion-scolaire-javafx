package miniProjet.dao;

import miniProjet.model.Groupe;
import java.sql.*;
import java.util.*;

public class GroupeDAO {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    // ✅ AJOUTER UN GROUPE (COLONNE AVEC ESPACE)
    public boolean create(Groupe g) {
        String sql = "INSERT INTO groupes (nom, niveau, `effectif maximum`) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, g.getNom());
            ps.setString(2, g.getNiveau());
            ps.setInt(3, g.getCapacite());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ LECTURE
    public List<Groupe> readAll() {
        List<Groupe> list = new ArrayList<>();
        String sql = "SELECT * FROM groupes";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Groupe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("niveau"),
                    rs.getInt("effectif maximum") // ← AVEC ESPACE
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ MODIFICATION
    public boolean update(Groupe g) {
        String sql = "UPDATE groupes SET nom=?, niveau=?, `effectif maximum`=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, g.getNom());
            ps.setString(2, g.getNiveau());
            ps.setInt(3, g.getCapacite());
            ps.setInt(4, g.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ SUPPRESSION
    public boolean delete(int id) {
        String sql = "DELETE FROM groupes WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
