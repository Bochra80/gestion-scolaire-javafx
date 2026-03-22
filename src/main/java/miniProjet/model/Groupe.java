package miniProjet.model;

public class Groupe {

    private int id;
    private String nom;
    private String niveau;
    private int capacite;

    public Groupe() {}

    public Groupe(int id, String nom, String niveau, int capacite) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
        this.capacite = capacite;
    }

    public Groupe(String nom, String niveau, int capacite) {
        this.nom = nom;
        this.niveau = niveau;
        this.capacite = capacite;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getNiveau() { return niveau; }
    public int getCapacite() { return capacite; }

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    @Override
    public String toString() {
        // EXACTEMENT comme phpMyAdmin : "1 - F"
        return id + " - " + nom;
    }
}
