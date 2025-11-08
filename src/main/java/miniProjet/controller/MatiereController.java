// MatiereController.java
package miniProjet.controller;

import miniProjet.dao.MatiereDAO;
import miniProjet.model.Matiere;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MatiereController {
    @FXML private TextField txtNom, txtCode, txtCoefficient, txtVolumeHoraire;
    @FXML private TableView<Matiere> tableMatieres;
    @FXML private TableColumn<Matiere, Integer> colId, colCoefficient, colVolumeHoraire;
    @FXML private TableColumn<Matiere, String> colNom, colCode;
    
    private MatiereDAO matiereDAO;
    private ObservableList<Matiere> matieresList;
    private Matiere selectedMatiere;
    
    @FXML
    public void initialize() {
        matiereDAO = new MatiereDAO();
        matieresList = FXCollections.observableArrayList();
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCoefficient.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        colVolumeHoraire.setCellValueFactory(new PropertyValueFactory<>("volumeHoraire"));
        
        refreshTable();
        tableMatieres.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newVal) -> {
                if (newVal != null) {
                    selectedMatiere = newVal;
                    fillForm(newVal);
                }
            }
        );
    }
    
    @FXML
    private void handleAjouter() {
        if (!validateInput()) return;
        Matiere m = new Matiere(txtNom.getText(), txtCode.getText(),
            Integer.parseInt(txtCoefficient.getText()), Integer.parseInt(txtVolumeHoraire.getText()));
        if (matiereDAO.create(m)) {
            new Alert(Alert.AlertType.INFORMATION, "Matière ajoutée!").showAndWait();
            refreshTable();
            clearForm();
        }
    }
    
    @FXML
    private void handleModifier() {
        if (selectedMatiere == null) {
            new Alert(Alert.AlertType.WARNING, "Sélectionnez une matière").showAndWait();
            return;
        }
        if (!validateInput()) return;
        selectedMatiere.setNom(txtNom.getText());
        selectedMatiere.setCode(txtCode.getText());
        selectedMatiere.setCoefficient(Integer.parseInt(txtCoefficient.getText()));
        selectedMatiere.setVolumeHoraire(Integer.parseInt(txtVolumeHoraire.getText()));
        if (matiereDAO.update(selectedMatiere)) {
            new Alert(Alert.AlertType.INFORMATION, "Matière modifiée!").showAndWait();
            refreshTable();
            clearForm();
        }
    }
    
    @FXML
    private void handleSupprimer() {
        if (selectedMatiere == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            if (matiereDAO.delete(selectedMatiere.getId())) {
                new Alert(Alert.AlertType.INFORMATION, "Supprimé!").showAndWait();
                refreshTable();
                clearForm();
            }
        }
    }
    
    @FXML private void handleActualiser() { refreshTable(); clearForm(); }
    @FXML private void handleVider() { clearForm(); }
    
    private void refreshTable() {
        matieresList.setAll(matiereDAO.readAll());
        tableMatieres.setItems(matieresList);
    }
    
    private void fillForm(Matiere m) {
        txtNom.setText(m.getNom());
        txtCode.setText(m.getCode());
        txtCoefficient.setText(String.valueOf(m.getCoefficient()));
        txtVolumeHoraire.setText(String.valueOf(m.getVolumeHoraire()));
    }
    
    private void clearForm() {
        txtNom.clear(); txtCode.clear(); txtCoefficient.clear(); txtVolumeHoraire.clear();
        selectedMatiere = null;
        tableMatieres.getSelectionModel().clearSelection();
    }
    
    private boolean validateInput() {
        if (txtNom.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Nom obligatoire").showAndWait();
            return false;
        }
        try {
            Integer.parseInt(txtCoefficient.getText());
            Integer.parseInt(txtVolumeHoraire.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Valeurs numériques invalides").showAndWait();
            return false;
        }
        return true;
    }
}