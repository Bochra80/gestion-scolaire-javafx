package miniProjet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    // 🔧 TAILLE UNIQUE POUR TOUTE L'APPLICATION
    public static final double WINDOW_WIDTH = 950;
    public static final double WINDOW_HEIGHT = 550;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/miniProjet/view/MainView.fxml")
            );

            Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);

            primaryStage.setTitle("Gestion Scolaire - Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}