package utils;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost/sistema_vuelos";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; 
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("¡Conexión exitosa a la base de datos!");
            } catch (ClassNotFoundException e) {
                System.err.println("Error: No se encontró el driver MySQL");
                System.err.println("Debes agregar mysql-connector-java.jar al proyecto");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Error de conexión a la base de datos:");
                System.err.println("URL: " + URL);
                System.err.println("Usuario: " + USER);
                System.err.println("Mensaje: " + e.getMessage());
            }
        }
        return connection;
    }
    
    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Base de datos conectada correctamente");
        } else {
            System.out.println("❌ No se pudo conectar a la base de datos");
        }
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}