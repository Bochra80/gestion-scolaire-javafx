package miniProjet.model;

public class Matiere {

    private int id;
    private String nom;
    private String code;
    private int coefficient;
    private int volumeHoraire;
    private String description;

    public Matiere() {}

    // ✅ utilisé par SELECT *
    public Matiere(int id, String nom, String code, int coefficient, int volumeHoraire) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.coefficient = coefficient;
        this.volumeHoraire = volumeHoraire;
    }

    public Matiere(String nom, String code, int coefficient, int volumeHoraire) {
        this.nom = nom;
        this.code = code;
        this.coefficient = coefficient;
        this.volumeHoraire = volumeHoraire;
    }

    // getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getCode() { return code; }
    public int getCoefficient() { return coefficient; }
    public int getVolumeHoraire() { return volumeHoraire; }

    // setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setCode(String code) { this.code = code; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }
    public void setVolumeHoraire(int volumeHoraire) { this.volumeHoraire = volumeHoraire; }

    @Override
    public String toString() {
        return nom;
    }
}
