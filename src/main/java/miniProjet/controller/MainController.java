package miniProjet.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
    
    @FXML
    private void handleGestionEtudiants() {
        openWindow("Gestion des Étudiants", "/view/EtudiantView.fxml");
    }
    
    @FXML
    private void handleGestionEnseignants() {
        openWindow("Gestion des Enseignants", "/view/EnseignantView.fxml");
    }
    
    @FXML
    private void handleGestionMatieres() {
        openWindow("Gestion des Matières", "/view/MatiereView.fxml");
    }
    
    @FXML
    private void handleGestionGroupes() {
        openWindow("Gestion des Groupes", "/view/GroupeView.fxml");
    }
    
    @FXML
    private void handleEmploiDuTemps() {
        showInfo("Emploi du Temps", "Fonctionnalité en développement");
    }
    
    @FXML
    private void handleQuitter() {
        System.exit(0);
    }
    
    private void openWindow(String title, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showError("Erreur", "Impossible d'ouvrir la fenêtre: " + e.getMessage());
        }
    }
    
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}