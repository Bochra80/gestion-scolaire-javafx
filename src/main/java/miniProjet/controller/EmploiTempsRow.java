package miniProjet.controller;

public class EmploiTempsRow {

    private int id;
    private String groupe;
    private String matiere;
    private String enseignant;
    private String jour;
    private String heureDebut;
    private String heureFin;
    private String salle;

    public EmploiTempsRow(int id, String groupe, String matiere,
                          String enseignant, String jour,
                          String heureDebut, String heureFin, String salle) {
        this.id = id;
        this.groupe = groupe;
        this.matiere = matiere;
        this.enseignant = enseignant;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
    }

    public int getId() { return id; }
    public String getGroupe() { return groupe; }
    public String getMatiere() { return matiere; }
    public String getEnseignant() { return enseignant; }
    public String getJour() { return jour; }
    public String getHeureDebut() { return heureDebut; }
    public String getHeureFin() { return heureFin; }
    public String getSalle() { return salle; }
}
