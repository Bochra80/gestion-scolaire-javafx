package miniProjet.dao;

import miniProjet.model.Matiere;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatiereDAO {

    private final Connection conn;

    public MatiereDAO() {
        conn = DatabaseConnection.getInstance().getConnection();
    }

    public List<Matiere> readAll() {
        List<Matiere> list = new ArrayList<>();
        String sql = "SELECT * FROM matieres";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Matiere(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("code"),
                        rs.getInt("coefficient"),
                        rs.getInt("volume_horaire")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void create(Matiere m) {
        String sql =
                "INSERT INTO matieres (nom, code, coefficient, volume_horaire) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getCode());
            ps.setInt(3, m.getCoefficient());
            ps.setInt(4, m.getVolumeHoraire());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Matiere m) {
        String sql =
                "UPDATE matieres SET nom=?, code=?, coefficient=?, volume_horaire=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getCode());
            ps.setInt(3, m.getCoefficient());
            ps.setInt(4, m.getVolumeHoraire());
            ps.setInt(5, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement ps =
                     conn.prepareStatement("DELETE FROM matieres WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Matiere> search(String key) {
        List<Matiere> list = new ArrayList<>();
        String sql =
                "SELECT * FROM matieres WHERE nom LIKE ? OR code LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Matiere(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("code"),
                        rs.getInt("coefficient"),
                        rs.getInt("volume_horaire")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
