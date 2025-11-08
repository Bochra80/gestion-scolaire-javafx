package miniProjet.controller;

import miniProjet.dao.EnseignantDAO;
import miniProjet.model.Enseignant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EnseignantController {
    
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelephone;
    @FXML private TextField txtSpecialite;
    @FXML private TextField txtRecherche;
    
    @FXML private TableView<Enseignant> tableEnseignants;
    @FXML private TableColumn<Enseignant, Integer> colId;
    @FXML private TableColumn<Enseignant, String> colNom;
    @FXML private TableColumn<Enseignant, String> colPrenom;
    @FXML private TableColumn<Enseignant, String> colEmail;
    @FXML private TableColumn<Enseignant, String> colTelephone;
    @FXML private TableColumn<Enseignant, String> colSpecialite;
    
    private EnseignantDAO enseignantDAO;
    private ObservableList<Enseignant> enseignantsList;
    private Enseignant selectedEnseignant;
    
    @FXML
    public void initialize() {
        enseignantDAO = new EnseignantDAO();
        enseignantsList = FXCollections.observableArrayList();
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colSpecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        
        refreshTable();
        
        tableEnseignants.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedEnseignant = newSelection;
                    fillForm(newSelection);
                }
            }
        );
    }
    
    @FXML
    private void handleAjouter() {
        if (!validateInput()) return;
        
        Enseignant enseignant = new Enseignant(
            txtNom.getText(), txtPrenom.getText(), txtEmail.getText(),
            txtTelephone.getText(), txtSpecialite.getText()
        );
        
        if (enseignantDAO.create(enseignant)) {
            showSuccess("Enseignant ajouté avec succès!");
            refreshTable();
            clearForm();
        } else {
            showError("Erreur lors de l'ajout");
        }
    }
    
    @FXML
    private void handleModifier() {
        if (selectedEnseignant == null) {
            showWarning("Sélectionnez un enseignant");
            return;
        }
        if (!validateInput()) return;
        
        selectedEnseignant.setNom(txtNom.getText());
        selectedEnseignant.setPrenom(txtPrenom.getText());
        selectedEnseignant.setEmail(txtEmail.getText());
        selectedEnseignant.setTelephone(txtTelephone.getText());
        selectedEnseignant.setSpecialite(txtSpecialite.getText());
        
        if (enseignantDAO.update(selectedEnseignant)) {
            showSuccess("Enseignant modifié!");
            refreshTable();
            clearForm();
        } else {
            showError("Erreur modification");
        }
    }
    
    @FXML
    private void handleSupprimer() {
        if (selectedEnseignant == null) {
            showWarning("Sélectionnez un enseignant");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setContentText("Supprimer " + selectedEnseignant.getNom() + "?");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            if (enseignantDAO.delete(selectedEnseignant.getId())) {
                showSuccess("Enseignant supprimé!");
                refreshTable();
                clearForm();
            } else {
                showError("Erreur suppression");
            }
        }
    }
    
    @FXML
    private void handleRechercher() {
        String keyword = txtRecherche.getText().trim();
        if (keyword.isEmpty()) {
            refreshTable();
        } else {
            enseignantsList.setAll(enseignantDAO.search(keyword));
            tableEnseignants.setItems(enseignantsList);
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
        enseignantsList.setAll(enseignantDAO.readAll());
        tableEnseignants.setItems(enseignantsList);
    }
    
    private void fillForm(Enseignant e) {
        txtNom.setText(e.getNom());
        txtPrenom.setText(e.getPrenom());
        txtEmail.setText(e.getEmail());
        txtTelephone.setText(e.getTelephone());
        txtSpecialite.setText(e.getSpecialite());
    }
    
    private void clearForm() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtTelephone.clear();
        txtSpecialite.clear();
        txtRecherche.clear();
        selectedEnseignant = null;
        tableEnseignants.getSelectionModel().clearSelection();
    }
    
    private boolean validateInput() {
        if (txtNom.getText().trim().isEmpty() || txtPrenom.getText().trim().isEmpty()) {
            showWarning("Nom et prénom obligatoires");
            return false;
        }
        return true;
    }
    
    private void showSuccess(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
    
    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
    
    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}