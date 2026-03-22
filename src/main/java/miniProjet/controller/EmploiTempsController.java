package miniProjet.controller;
import miniProjet.controller.MainController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import miniProjet.dao.*;
import miniProjet.model.*;

public class EmploiTempsController {

    @FXML private ComboBox<Groupe> cbGroupe;
    @FXML private ComboBox<Matiere> cbMatiere;
    @FXML private ComboBox<Enseignant> cbEnseignant;
    @FXML private ComboBox<String> cbJour;

    @FXML private TextField txtHeureDebut, txtHeureFin, txtSalle;

    @FXML private TableView<EmploiTempsRow> tableEmplois;
    @FXML private TableColumn<EmploiTempsRow, Integer> colId;
    @FXML private TableColumn<EmploiTempsRow, String> colGroupe, colMatiere,
            colEnseignant, colJour, colDebut, colFin, colSalle;

    private final EmploiTempsDAO emploiDAO = new EmploiTempsDAO();
    private final GroupeDAO groupeDAO = new GroupeDAO();
    private final MatiereDAO matiereDAO = new MatiereDAO();
    private final EnseignantDAO enseignantDAO = new EnseignantDAO();

    @FXML
    public void initialize() {

        cbJour.setItems(FXCollections.observableArrayList(
                "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"
        ));

        cbGroupe.setItems(FXCollections.observableArrayList(groupeDAO.readAll()));
        cbMatiere.setItems(FXCollections.observableArrayList(matiereDAO.readAll()));
        cbEnseignant.setItems(FXCollections.observableArrayList(enseignantDAO.readAll()));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGroupe.setCellValueFactory(new PropertyValueFactory<>("groupe"));
        colMatiere.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        colEnseignant.setCellValueFactory(new PropertyValueFactory<>("enseignant"));
        colJour.setCellValueFactory(new PropertyValueFactory<>("jour"));
        colDebut.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        colSalle.setCellValueFactory(new PropertyValueFactory<>("salle"));

        refreshTable();
    }

    @FXML
    private void handleAjouter() {

        if (cbGroupe.getValue() == null || cbMatiere.getValue() == null ||
                cbEnseignant.getValue() == null || cbJour.getValue() == null) {
            alert("Tous les champs doivent être sélectionnés");
            return;
        }

        boolean ok = emploiDAO.insert(
                cbGroupe.getValue().getId(),
                cbMatiere.getValue().getId(),
                cbEnseignant.getValue().getId(),
                cbJour.getValue(),
                txtHeureDebut.getText(),
                txtHeureFin.getText(),
                txtSalle.getText()
        );

        if (!ok) {
            alert("Erreur insertion (format heure HH:mm)");
            return;
        }

        refreshTable();
        clearForm();
    }

    @FXML
    private void handleSupprimer() {
        EmploiTempsRow row = tableEmplois.getSelectionModel().getSelectedItem();
        if (row != null) {
            emploiDAO.delete(row.getId());
            refreshTable();
        }
    }

    @FXML
    private void handleActualiser() {
        refreshTable();
    }

    @FXML
    private void handleVider() {
        clearForm();
    }

    @FXML
    private void handleRetourMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        MainController.showMainMenu(stage);
    }


    private void refreshTable() {
        tableEmplois.setItems(
                FXCollections.observableArrayList(emploiDAO.readAll())
        );
    }

    private void clearForm() {
        cbGroupe.setValue(null);
        cbMatiere.setValue(null);
        cbEnseignant.setValue(null);
        cbJour.setValue(null);
        txtHeureDebut.clear();
        txtHeureFin.clear();
        txtSalle.clear();
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}