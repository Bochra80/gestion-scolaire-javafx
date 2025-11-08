package miniProjet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_scolarite";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Connexion à la base de données réussie!");
        } catch (ClassNotFoundException e) {
            System.err.println(" Driver MySQL non trouvé: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println(" Erreur de connexion: " + e.getMessage());
        }
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération connexion: " + e.getMessage());
        }
        return connection;
    }
}