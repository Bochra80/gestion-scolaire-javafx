// GroupeController.java
package miniProjet.controller;

import miniProjet.dao.GroupeDAO;
import miniProjet.model.Groupe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GroupeController {
    @FXML private TextField txtNom, txtNiveau, txtCapacite;
    @FXML private TableView<Groupe> tableGroupes;
    @FXML private TableColumn<Groupe, Integer> colId, colCapacite;
    @FXML private TableColumn<Groupe, String> colNom, colNiveau;
    
    private GroupeDAO groupeDAO;
    private ObservableList<Groupe> groupesList;
    private Groupe selectedGroupe;
    
    @FXML
    public void initialize() {
        groupeDAO = new GroupeDAO();
        groupesList = FXCollections.observableArrayList();
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        
        refreshTable();
        tableGroupes.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newVal) -> {
                if (newVal != null) {
                    selectedGroupe = newVal;
                    fillForm(newVal);
                }
            }
        );
    }
    
    @FXML
    private void handleAjouter() {
        if (!validateInput()) return;
        Groupe g = new Groupe(txtNom.getText(), txtNiveau.getText(), Integer.parseInt(txtCapacite.getText()));
        if (groupeDAO.create(g)) {
            new Alert(Alert.AlertType.INFORMATION, "Groupe ajouté!").showAndWait();
            refreshTable();
            clearForm();
        }
    }
    
    @FXML
    private void handleModifier() {
        if (selectedGroupe == null) {
            new Alert(Alert.AlertType.WARNING, "Sélectionnez un groupe").showAndWait();
            return;
        }
        if (!validateInput()) return;
        selectedGroupe.setNom(txtNom.getText());
        selectedGroupe.setNiveau(txtNiveau.getText());
        selectedGroupe.setCapacite(Integer.parseInt(txtCapacite.getText()));
        if (groupeDAO.update(selectedGroupe)) {
            new Alert(Alert.AlertType.INFORMATION, "Groupe modifié!").showAndWait();
            refreshTable();
            clearForm();
        }
    }
    
    @FXML
    private void handleSupprimer() {
        if (selectedGroupe == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            if (groupeDAO.delete(selectedGroupe.getId())) {
                new Alert(Alert.AlertType.INFORMATION, "Supprimé!").showAndWait();
                refreshTable();
                clearForm();
            }
        }
    }
    
    @FXML private void handleActualiser() { refreshTable(); clearForm(); }
    @FXML private void handleVider() { clearForm(); }
    
    private void refreshTable() {
        groupesList.setAll(groupeDAO.readAll());
        tableGroupes.setItems(groupesList);
    }
    
    private void fillForm(Groupe g) {
        txtNom.setText(g.getNom());
        txtNiveau.setText(g.getNiveau());
        txtCapacite.setText(String.valueOf(g.getCapacite()));
    }
    
    private void clearForm() {
        txtNom.clear(); txtNiveau.clear(); txtCapacite.clear();
        selectedGroupe = null;
        tableGroupes.getSelectionModel().clearSelection();
    }
    
    private boolean validateInput() {
        if (txtNom.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Nom obligatoire").showAndWait();
            return false;
        }
        try {
            Integer.parseInt(txtCapacite.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Capacité invalide").showAndWait();
            return false;
        }
        return true;
    }
}