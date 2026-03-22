package miniProjet.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import miniProjet.App;
import miniProjet.dao.*;

public class MainController {

    @FXML private Label lblEtudiants;
    @FXML private Label lblEnseignants;
    @FXML private Label lblMatieres;
    @FXML private Label lblGroupes;

    @FXML
    public void initialize() {
        refreshStatistics();
    }

    private void loadStatistics() {
        try {
            lblEtudiants.setText(String.valueOf(new EtudiantDAO().readAll().size()));
            lblEnseignants.setText(String.valueOf(new EnseignantDAO().readAll().size()));
            lblMatieres.setText(String.valueOf(new MatiereDAO().readAll().size()));
            lblGroupes.setText(String.valueOf(new GroupeDAO().readAll().size()));
        } catch (Exception e) {
            lblEtudiants.setText("0");
            lblEnseignants.setText("0");
            lblMatieres.setText("0");
            lblGroupes.setText("0");
        }
    }

    public void refreshStatistics() {
        loadStatistics();
    }

    // 🔥 MÉTHODE UNIQUE POUR AFFICHER LE MENU
    public static void showMainMenu(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainController.class.getResource("/miniProjet/view/MainView.fxml")
            );

            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.refreshStatistics();

            stage.setScene(new Scene(root, App.WINDOW_WIDTH, App.WINDOW_HEIGHT));
            stage.setTitle("Gestion Scolaire - Menu Principal");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleGestionEtudiants(ActionEvent e) {
        loadView(e, "/miniProjet/view/EtudiantView.fxml", "Gestion des Étudiants");
    }

    @FXML private void handleGestionEnseignants(ActionEvent e) {
        loadView(e, "/miniProjet/view/EnseignantView.fxml", "Gestion des Enseignants");
    }

    @FXML private void handleGestionMatieres(ActionEvent e) {
        loadView(e, "/miniProjet/view/MatiereView.fxml", "Gestion des Matières");
    }

    @FXML private void handleGestionGroupes(ActionEvent e) {
        loadView(e, "/miniProjet/view/GroupeView.fxml", "Gestion des Groupes");
    }

    @FXML private void handleEmploiDuTemps(ActionEvent e) {
        loadView(e, "/miniProjet/view/EmploiTempsView.fxml", "Gestion des Emplois du Temps");
    }

    @FXML private void handleQuitter() {
        System.exit(0);
    }

    private void loadView(ActionEvent event, String path, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, App.WINDOW_WIDTH, App.WINDOW_HEIGHT));
            stage.setTitle(title);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Impossible de charger la vue").showAndWait();
        }
    }
}
