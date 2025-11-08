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
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    
    @Override
    public String toString() { return nom; }
}