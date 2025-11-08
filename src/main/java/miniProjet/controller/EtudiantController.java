package miniProjet.controller;

import miniProjet.dao.EtudiantDAO;
import miniProjet.model.Etudiant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EtudiantController {
    
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtDateNaissance;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelephone;
    @FXML private TextField txtRecherche;
    
    @FXML private TableView<Etudiant> tableEtudiants;
    @FXML private TableColumn<Etudiant, Integer> colId;
    @FXML private TableColumn<Etudiant, String> colNom;
    @FXML private TableColumn<Etudiant, String> colPrenom;
    @FXML private TableColumn<Etudiant, String> colDateNaissance;
    @FXML private TableColumn<Etudiant, String> colEmail;
    @FXML private TableColumn<Etudiant, String> colTelephone;
    
    private EtudiantDAO etudiantDAO;
    private ObservableList<Etudiant> etudiantsList;
    private Etudiant selectedEtudiant;
    
    @FXML
    public void initialize() {
        etudiantDAO = new EtudiantDAO();
        etudiantsList = FXCollections.observableArrayList();
        
        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        
        // Charger les données
        refreshTable();
        
        // Listener pour la sélection dans la table
        tableEtudiants.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedEtudiant = newSelection;
                    fillForm(newSelection);
                }
            }
        );
    }
    
    @FXML
    private void handleAjouter() {
        if (!validateInput()) {
            return;
        }
        
        Etudiant etudiant = new Etudiant(
            txtNom.getText(),
            txtPrenom.getText(),
            txtDateNaissance.getText(),
            txtEmail.getText(),
            txtTelephone.getText()
        );
        
        if (etudiantDAO.create(etudiant)) {
            showSuccess("Étudiant ajouté avec succès!");
            refreshTable();
            clearForm();
        } else {
            showError("Erreur lors de l'ajout de l'étudiant");
        }
    }
    
    @FXML
    private void handleModifier() {
        if (selectedEtudiant == null) {
            showWarning("Veuillez sélectionner un étudiant à modifier");
            return;
        }
        
        if (!validateInput()) {
            return;
        }
        
        selectedEtudiant.setNom(txtNom.getText());
        selectedEtudiant.setPrenom(txtPrenom.getText());
        selectedEtudiant.setDateNaissance(txtDateNaissance.getText());
        selectedEtudiant.setEmail(txtEmail.getText());
        selectedEtudiant.setTelephone(txtTelephone.getText());
        
        if (etudiantDAO.update(selectedEtudiant)) {
            showSuccess("Étudiant modifié avec succès!");
            refreshTable();
            clearForm();
        } else {
            showError("Erreur lors de la modification de l'étudiant");
        }
    }
    
    @FXML
    private void handleSupprimer() {
        if (selectedEtudiant == null) {
            showWarning("Veuillez sélectionner un étudiant à supprimer");
            return;
        }
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer l'étudiant?");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer " + 
                                   selectedEtudiant.getNom() + " " + selectedEtudiant.getPrenom() + "?");
        
        if (confirmation.showAndWait().get() == ButtonType.OK) {
            if (etudiantDAO.delete(selectedEtudiant.getId())) {
                showSuccess("Étudiant supprimé avec succès!");
                refreshTable();
                clearForm();
            } else {
                showError("Erreur lors de la suppression de l'étudiant");
            }
        }
    }
    
    @FXML
    private void handleRechercher() {
        String keyword = txtRecherche.getText().trim();
        if (keyword.isEmpty()) {
            refreshTable();
        } else {
            etudiantsList.setAll(etudiantDAO.search(keyword));
            tableEtudiants.setItems(etudiantsList);
        }
    }
    
    @FXML
    private void handleActualiser() {
        refreshTable();
        clearForm();
    }
    
    @FXML
    private void handleVider() {
        clearForm();
    }
    
    private void refreshTable() {
        etudiantsList.setAll(etudiantDAO.readAll());
        tableEtudiants.setItems(etudiantsList);
    }
    
    private void fillForm(Etudiant etudiant) {
        txtNom.setText(etudiant.getNom());
        txtPrenom.setText(etudiant.getPrenom());
        txtDateNaissance.setText(etudiant.getDateNaissance());
        txtEmail.setText(etudiant.getEmail());
        txtTelephone.setText(etudiant.getTelephone());
    }
    
    private void clearForm() {
        txtNom.clear();
        txtPrenom.clear();
        txtDateNaissance.clear();
        txtEmail.clear();
        txtTelephone.clear();
        txtRecherche.clear();
        selectedEtudiant = null;
        tableEtudiants.getSelectionModel().clearSelection();
    }
    
    private boolean validateInput() {
        if (txtNom.getText().trim().isEmpty()) {
            showWarning("Le nom est obligatoire");
            return false;
        }
        if (txtPrenom.getText().trim().isEmpty()) {
            showWarning("Le prénom est obligatoire");
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            showWarning("L'email est obligatoire");
            return false;
        }
        return true;
    }
    
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}