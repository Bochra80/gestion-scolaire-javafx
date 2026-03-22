package miniProjet.dao;

import miniProjet.controller.EmploiTempsRow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploiTempsDAO {

    public boolean insert(int groupeId, int matiereId, int enseignantId,
                          String jour, String hDebut, String hFin, String salle) {

        String sql = """
            INSERT INTO emplois_temps
            (groupe, matiere, enseignant, jour, heure_debut, heure_fin, salle)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, groupeId);
            ps.setInt(2, matiereId);
            ps.setInt(3, enseignantId);
            ps.setString(4, jour);

            // ⚠️ Time.valueOf exige HH:mm:ss
            ps.setTime(5, Time.valueOf(hDebut + ":00"));
            ps.setTime(6, Time.valueOf(hFin + ":00"));

            ps.setString(7, salle);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<EmploiTempsRow> readAll() {

        List<EmploiTempsRow> list = new ArrayList<>();

        String sql = """
            SELECT et.id,
                   g.nom AS groupe,
                   m.nom AS matiere,
                   e.nom AS enseignant,
                   et.jour,
                   et.heure_debut,
                   et.heure_fin,
                   et.salle
            FROM emplois_temps et
            JOIN groupes g ON et.groupe = g.id
            JOIN matieres m ON et.matiere = m.id
            JOIN enseignants e ON et.enseignant = e.id
        """;

        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new EmploiTempsRow(
                        rs.getInt("id"),
                        rs.getString("groupe"),
                        rs.getString("matiere"),
                        rs.getString("enseignant"),
                        rs.getString("jour"),
                        rs.getTime("heure_debut").toString(),
                        rs.getTime("heure_fin").toString(),
                        rs.getString("salle")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(int id) {
        try (Connection cn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps =
                     cn.prepareStatement("DELETE FROM emplois_temps WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
