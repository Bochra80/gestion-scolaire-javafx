package miniProjet.controller;
import miniProjet.controller.MainController;
import miniProjet.dao.GroupeDAO;
import miniProjet.model.Groupe;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class GroupeController {

    @FXML private TextField txtId;
    @FXML private TextField txtNom;
    @FXML private TextField txtNiveau;
    @FXML private TextField txtEffectifMax;

    @FXML private TableView<Groupe> tableGroupes;
    @FXML private TableColumn<Groupe, Integer> colId;
    @FXML private TableColumn<Groupe, String> colNom;
    @FXML private TableColumn<Groupe, String> colNiveau;
    @FXML private TableColumn<Groupe, Integer> colEffectifMax;

    private GroupeDAO groupeDAO;
    private ObservableList<Groupe> groupes;
    private Groupe selectedGroupe;

    @FXML
    public void initialize() {
        groupeDAO = new GroupeDAO();
        groupes = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        colEffectifMax.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        refreshTable();

        tableGroupes.getSelectionModel().selectedItemProperty().addListener(
                (obs, o, g) -> { if (g != null) fillForm(g); }
        );
    }

    @FXML
    private void handleAjouter() {
        if (!validate()) return;

        Groupe g = new Groupe(
                txtNom.getText().trim(),
                txtNiveau.getText().trim(),
                Integer.parseInt(txtEffectifMax.getText().trim())
        );

        if (groupeDAO.create(g)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Groupe ajouté avec succès");
            refreshTable();
            clearForm();
        }
    }

    @FXML
    private void handleModifier() {
        if (selectedGroupe == null) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Sélectionnez un groupe");
            return;
        }

        if (!validate()) return;

        selectedGroupe.setNom(txtNom.getText().trim());
        selectedGroupe.setNiveau(txtNiveau.getText().trim());
        selectedGroupe.setCapacite(Integer.parseInt(txtEffectifMax.getText().trim()));

        groupeDAO.update(selectedGroupe);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleSupprimer() {
        if (selectedGroupe == null) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Sélectionnez un groupe");
            return;
        }

        groupeDAO.delete(selectedGroupe.getId());
        refreshTable();
        clearForm();
    }

    @FXML private void handleActualiser() { refreshTable(); }
    @FXML private void handleVider() { clearForm(); }

    @FXML
    private void handleRetourMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        MainController.showMainMenu(stage);
    }


    private void refreshTable() {
        groupes.setAll(groupeDAO.readAll());
        tableGroupes.setItems(groupes);
    }

    private void fillForm(Groupe g) {
        selectedGroupe = g;
        txtId.setText(String.valueOf(g.getId()));
        txtNom.setText(g.getNom());
        txtNiveau.setText(g.getNiveau());
        txtEffectifMax.setText(String.valueOf(g.getCapacite()));
    }

    private void clearForm() {
        txtId.clear();
        txtNom.clear();
        txtNiveau.clear();
        txtEffectifMax.clear();
        selectedGroupe = null;
        tableGroupes.getSelectionModel().clearSelection();
    }

    private boolean validate() {
        if (txtNom.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Nom obligatoire");
            return false;
        }
        if (txtNiveau.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Niveau obligatoire");
            return false;
        }
        if (txtEffectifMax.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Effectif obligatoire");
            return false;
        }
        try {
            Integer.parseInt(txtEffectifMax.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Effectif doit être un nombre");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}