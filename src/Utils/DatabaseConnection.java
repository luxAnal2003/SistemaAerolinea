package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost/sistema_vuelos";

    private static final String USER = "root";

    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        try {
            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );
            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        } catch (ClassNotFoundException e) {

            System.err.println(
                    "Error: Driver MySQL no encontrado"
            );
            e.printStackTrace();
        } catch (SQLException e) {

            System.err.println(
                    "Error de conexión: "
                    + e.getMessage()
            );
        }
        return null;
    }

    public static void testConnection() {
        try (Connection conn
                = getConnection()) {
            if (conn != null) {
                System.out.println(
                        "Base de datos conectada correctamente"
                );
            } else {
                System.out.println(
                        "No se pudo conectar"
                );
            }
        } catch (SQLException e) {
            System.err.println(
                    e.getMessage()
            );
        }
    }
}
