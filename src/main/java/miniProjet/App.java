package miniProjet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/miniProjet/view/MainView.fxml"));
            Scene scene = new Scene(loader.load(), 600, 500);
            
            primaryStage.setTitle("Gestion Scolaire - Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur au démarrage: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}