module miniProjet {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;   // ✅ OBLIGATOIRE (Scene, Stage, Node)
    requires java.sql;

    opens miniProjet to javafx.fxml;
    opens miniProjet.controller to javafx.fxml;
    opens miniProjet.dao to javafx.fxml, javafx.base;
    opens miniProjet.model to javafx.fxml, javafx.base;

    exports miniProjet;
    exports miniProjet.controller;
}
