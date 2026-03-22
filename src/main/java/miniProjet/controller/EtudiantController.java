package miniProjet.controller;
import miniProjet.controller.MainController;
import miniProjet.dao.EtudiantDAO;
import miniProjet.model.Etudiant;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class EtudiantController {

    @FXML private TextField txtId;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtDateNaissance;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelephone;
    @FXML private TextField txtAdresse;
    @FXML private TextField txtRecherche;

    @FXML private TableView<Etudiant> tableEtudiants;
    @FXML private TableColumn<Etudiant, Integer> colId;
    @FXML private TableColumn<Etudiant, String> colNom;
    @FXML private TableColumn<Etudiant, String> colPrenom;
    @FXML private TableColumn<Etudiant, String> colDateNaissance;
    @FXML private TableColumn<Etudiant, String> colEmail;
    @FXML private TableColumn<Etudiant, String> colTelephone;
    @FXML private TableColumn<Etudiant, String> colAdresse;

    private EtudiantDAO etudiantDAO;
    private ObservableList<Etudiant> etudiants;
    private Etudiant selected;

    @FXML
    public void initialize() {
        etudiantDAO = new EtudiantDAO();
        etudiants = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        refreshTable();

        tableEtudiants.getSelectionModel().selectedItemProperty().addListener(
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

        Etudiant e = new Etudiant(
                txtNom.getText().trim(),
                txtPrenom.getText().trim(),
                txtDateNaissance.getText().trim(),
                txtEmail.getText().trim(),
                txtTelephone.getText().trim(),
                txtAdresse.getText().trim()
        );

        if (!txtId.getText().trim().isEmpty()) {
            e.setId(Integer.parseInt(txtId.getText().trim()));
            etudiantDAO.createWithId(e);
        } else {
            etudiantDAO.create(e);
        }

        refreshTable();
        clearForm();
    }

    @FXML
    private void handleModifier() {
        if (selected == null) return;

        selected.setNom(txtNom.getText().trim());
        selected.setPrenom(txtPrenom.getText().trim());
        selected.setDateNaissance(txtDateNaissance.getText().trim());
        selected.setEmail(txtEmail.getText().trim());
        selected.setTelephone(txtTelephone.getText().trim());
        selected.setAdresse(txtAdresse.getText().trim());

        etudiantDAO.update(selected);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleSupprimer() {
        if (selected == null) return;

        etudiantDAO.delete(selected.getId());
        refreshTable();
        clearForm();
    }

    @FXML private void handleActualiser() { refreshTable(); }
    @FXML private void handleVider() { clearForm(); }

    @FXML
    private void handleRechercher() {
        String key = txtRecherche.getText().trim();
        if (key.isEmpty()) refreshTable();
        else tableEtudiants.setItems(
                FXCollections.observableArrayList(etudiantDAO.search(key))
        );
    }

    @FXML
    private void handleRetourMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        MainController.showMainMenu(stage);
    }


    private void refreshTable() {
        etudiants.setAll(etudiantDAO.readAll());
        tableEtudiants.setItems(etudiants);
    }

    private void fillForm(Etudiant e) {
        txtId.setText(String.valueOf(e.getId()));
        txtNom.setText(e.getNom());
        txtPrenom.setText(e.getPrenom());
        txtDateNaissance.setText(e.getDateNaissance());
        txtEmail.setText(e.getEmail());
        txtTelephone.setText(e.getTelephone());
        txtAdresse.setText(e.getAdresse());
    }

    private void clearForm() {
        txtId.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtDateNaissance.clear();
        txtEmail.clear();
        txtTelephone.clear();
        txtAdresse.clear();
        txtRecherche.clear();
        selected = null;
        tableEtudiants.getSelectionModel().clearSelection();
    }

    private boolean validate() {
        return !txtNom.getText().isEmpty()
                && !txtPrenom.getText().isEmpty()
                && !txtDateNaissance.getText().isEmpty()
                && !txtEmail.getText().isEmpty();
    }
}