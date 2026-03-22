package miniProjet.controller;

import miniProjet.dao.MatiereDAO;
import miniProjet.model.Matiere;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MatiereController {

    @FXML private TextField txtId, txtNom, txtCode, txtCoefficient, txtVolumeHoraire, txtRecherche;

    @FXML private TableView<Matiere> tableMatieres;
    @FXML private TableColumn<Matiere, Integer> colId, colCoefficient, colVolumeHoraire;
    @FXML private TableColumn<Matiere, String> colNom, colCode;

    private final MatiereDAO matiereDAO = new MatiereDAO();
    private final ObservableList<Matiere> matieres = FXCollections.observableArrayList();
    private Matiere selected;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCoefficient.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        colVolumeHoraire.setCellValueFactory(new PropertyValueFactory<>("volumeHoraire"));

        refreshTable();

        tableMatieres.getSelectionModel().selectedItemProperty().addListener(
                (obs, o, n) -> { if (n != null) fillForm(n); }
        );
    }

    @FXML
    private void handleAjouter() {
        if (!validate()) return;
        matiereDAO.create(new Matiere(
                txtNom.getText(),
                txtCode.getText(),
                Integer.parseInt(txtCoefficient.getText()),
                Integer.parseInt(txtVolumeHoraire.getText())
        ));
        refreshTable(); clearForm();
    }

    @FXML
    private void handleModifier() {
        if (selected == null) return;
        selected.setNom(txtNom.getText());
        selected.setCode(txtCode.getText());
        selected.setCoefficient(Integer.parseInt(txtCoefficient.getText()));
        selected.setVolumeHoraire(Integer.parseInt(txtVolumeHoraire.getText()));
        matiereDAO.update(selected);
        refreshTable(); clearForm();
    }

    @FXML
    private void handleSupprimer() {
        if (selected == null) return;
        matiereDAO.delete(selected.getId());
        refreshTable(); clearForm();
    }

    @FXML private void handleActualiser() { refreshTable(); clearForm(); }
    @FXML private void handleVider() { clearForm(); }

    @FXML
    private void handleRechercher() {
        String key = txtRecherche.getText().trim();
        tableMatieres.setItems(
                key.isEmpty()
                        ? matieres
                        : FXCollections.observableArrayList(matiereDAO.search(key))
        );
    }

    @FXML
    private void handleRetourMenu(ActionEvent e) {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        MainController.showMainMenu(stage);
    }

    private void refreshTable() {
        matieres.setAll(matiereDAO.readAll());
        tableMatieres.setItems(matieres);
    }

    private void fillForm(Matiere m) {
        selected = m;
        txtId.setText(String.valueOf(m.getId()));
        txtNom.setText(m.getNom());
        txtCode.setText(m.getCode());
        txtCoefficient.setText(String.valueOf(m.getCoefficient()));
        txtVolumeHoraire.setText(String.valueOf(m.getVolumeHoraire()));
    }

    private void clearForm() {
        txtId.clear(); txtNom.clear(); txtCode.clear();
        txtCoefficient.clear(); txtVolumeHoraire.clear(); txtRecherche.clear();
        selected = null;
        tableMatieres.getSelectionModel().clearSelection();
    }

    private boolean validate() {
        try {
            Integer.parseInt(txtCoefficient.getText());
            Integer.parseInt(txtVolumeHoraire.getText());
            return !txtNom.getText().isEmpty() && !txtCode.getText().isEmpty();
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Champs numériques invalides").showAndWait();
            return false;
        }
    }
}
