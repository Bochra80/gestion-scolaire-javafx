package miniProjet.controller;
import miniProjet.controller.MainController;
import miniProjet.dao.EnseignantDAO;
import miniProjet.model.Enseignant;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class EnseignantController {

    @FXML private TextField txtId;
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
    private ObservableList<Enseignant> enseignants;
    private Enseignant selected;

    @FXML
    public void initialize() {
        enseignantDAO = new EnseignantDAO();
        enseignants = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colSpecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        refreshTable();

        tableEnseignants.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        selected = newVal;
                        fillForm(newVal);
                    }
                }
        );
    }

    @FXML
    private void handleAjouter() {
        if (!validate()) return;

        Enseignant e = new Enseignant(
                txtNom.getText().trim(),
                txtPrenom.getText().trim(),
                txtEmail.getText().trim(),
                txtTelephone.getText().trim(),
                txtSpecialite.getText().trim()
        );

        if (!txtId.getText().trim().isEmpty()) {
            try {
                e.setId(Integer.parseInt(txtId.getText().trim()));
            } catch (NumberFormatException ex) {
                showAlert("ID invalide", "L'ID doit être un nombre.");
                return;
            }
        }

        enseignantDAO.create(e);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleModifier() {
        if (selected == null) return;

        selected.setNom(txtNom.getText());
        selected.setPrenom(txtPrenom.getText());
        selected.setEmail(txtEmail.getText());
        selected.setTelephone(txtTelephone.getText());
        selected.setSpecialite(txtSpecialite.getText());

        enseignantDAO.update(selected);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleSupprimer() {
        if (selected == null) return;
        enseignantDAO.delete(selected.getId());
        refreshTable();
        clearForm();
    }

    @FXML private void handleActualiser() { refreshTable(); }
    @FXML private void handleVider() { clearForm(); }

    @FXML
    private void handleRechercher() {
        String key = txtRecherche.getText().trim();
        tableEnseignants.setItems(
                key.isEmpty()
                        ? enseignants
                        : FXCollections.observableArrayList(enseignantDAO.search(key))
        );
    }

    @FXML
    private void handleRetourMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        MainController.showMainMenu(stage);
    }


    private void refreshTable() {
        enseignants.setAll(enseignantDAO.readAll());
        tableEnseignants.setItems(enseignants);
    }

    private void fillForm(Enseignant e) {
        txtId.setText(String.valueOf(e.getId()));
        txtNom.setText(e.getNom());
        txtPrenom.setText(e.getPrenom());
        txtEmail.setText(e.getEmail());
        txtTelephone.setText(e.getTelephone());
        txtSpecialite.setText(e.getSpecialite());
    }

    private void clearForm() {
        txtId.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtTelephone.clear();
        txtSpecialite.clear();
        txtRecherche.clear();
        selected = null;
    }

    private boolean validate() {
        return !txtNom.getText().isEmpty()
                && !txtPrenom.getText().isEmpty();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}