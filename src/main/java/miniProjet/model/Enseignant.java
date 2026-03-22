package miniProjet.model;

public class Enseignant {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;

    public Enseignant() {}

    public Enseignant(int id, String nom, String prenom,
                      String email, String telephone, String specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.specialite = specialite;
    }

    public Enseignant(String nom, String prenom,
                      String email, String telephone, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.specialite = specialite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    @Override
    public String toString() {
        // "3 - Amira Ben Ali"
        return id + " - " + nom + " " + prenom;
    }
}
