module miniProjet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;              // ← AJOUT POUR JDBC
    requires mysql.connector.j;     // ← Pour MySQL (si tu l'utilises en dépendance Maven)

    opens miniProjet to javafx.fxml;
    opens miniProjet.controller to javafx.fxml;
    opens miniProjet.dao to javafx.fxml; // optionnel mais bon pour debug

    exports miniProjet;
    exports miniProjet.controller;
}
/*module miniProjet {
    requires javafx.controls;
    requires javafx.fxml;

    opens miniProjet to javafx.fxml; 
    opens miniProjet.controller to javafx.fxml; // ← AJOUT IMPORTANT

    exports miniProjet;
    exports miniProjet.controller;  // Optionnel mais recommandé si tu utilises le contrôleur ailleurs
}
 */

/*
 module miniProjet { 
 requires javafx.controls; 
 requires javafx.fxml; 
 opens miniProjet to javafx.fxml; 
 exports miniProjet; }
 */