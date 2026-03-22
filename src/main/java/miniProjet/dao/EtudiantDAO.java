package miniProjet.dao;

import miniProjet.model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EtudiantDAO {

    private Connection connection;

    public EtudiantDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();

        if (this.connection != null) {
            System.out.println("✅ EtudiantDAO initialisé avec connexion active");
        } else {
            System.err.println("❌ EtudiantDAO: Connexion NULL!");
        }
    }

    /* =====================================================
       MÉTHODE UTILITAIRE : formats de date FLEXIBLES
       ===================================================== */
    private LocalDate parseDateFlexible(String dateStr) {

        dateStr = dateStr.trim();

        DateTimeFormatter[] formats = {
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd")
        };

        for (DateTimeFormatter format : formats) {
            try {
                return LocalDate.parse(dateStr, format);
            } catch (DateTimeParseException ignored) {}
        }

        throw new IllegalArgumentException(
            "Format de date invalide. Utilise : jj-mm-aaaa ou aaaa-mm-jj"
        );
    }

    /* =====================
       CRÉER UN ÉTUDIANT (AUTO_INCREMENT)
       ===================== */
    public boolean create(Etudiant etudiant) {

        String sql = """
            INSERT INTO etudiants
            (nom, prenom, date_naissance, email, telephone, adresse)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, etudiant.getNom());
            stmt.setString(2, etudiant.getPrenom());
            stmt.setDate(3, Date.valueOf(parseDateFlexible(etudiant.getDateNaissance())));
            stmt.setString(4, etudiant.getEmail());
            stmt.setString(5, etudiant.getTelephone());
            stmt.setString(6, etudiant.getAdresse());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =====================
       CRÉER UN ÉTUDIANT (ID MANUEL)
       ===================== */
    public boolean createWithId(Etudiant etudiant) {

        String sql = """
            INSERT INTO etudiants
            (id, nom, prenom, date_naissance, email, telephone, adresse)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, etudiant.getId());
            stmt.setString(2, etudiant.getNom());
            stmt.setString(3, etudiant.getPrenom());
            stmt.setDate(4, Date.valueOf(parseDateFlexible(etudiant.getDateNaissance())));
            stmt.setString(5, etudiant.getEmail());
            stmt.setString(6, etudiant.getTelephone());
            stmt.setString(7, etudiant.getAdresse());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =====================
       LIRE TOUS LES ÉTUDIANTS
       ===================== */
    public List<Etudiant> readAll() {

        List<Etudiant> list = new ArrayList<>();
        String sql = "SELECT * FROM etudiants ORDER BY id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("date_naissance"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("adresse")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /* =====================
       METTRE À JOUR
       ===================== */
    public boolean update(Etudiant e) {

        String sql = """
            UPDATE etudiants
            SET nom=?, prenom=?, date_naissance=?, email=?, telephone=?, adresse=?
            WHERE id=?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getPrenom());
            stmt.setDate(3, Date.valueOf(parseDateFlexible(e.getDateNaissance())));
            stmt.setString(4, e.getEmail());
            stmt.setString(5, e.getTelephone());
            stmt.setString(6, e.getAdresse());
            stmt.setInt(7, e.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /* =====================
       SUPPRIMER
       ===================== */
    public boolean delete(int id) {

        try (PreparedStatement stmt =
             connection.prepareStatement("DELETE FROM etudiants WHERE id=?")) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =====================
       RECHERCHE
       ===================== */
    public List<Etudiant> search(String key) {

        List<Etudiant> list = new ArrayList<>();

        String sql = """
            SELECT * FROM etudiants
            WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ? OR adresse LIKE ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            String k = "%" + key + "%";
            stmt.setString(1, k);
            stmt.setString(2, k);
            stmt.setString(3, k);
            stmt.setString(4, k);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("date_naissance"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("adresse")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
