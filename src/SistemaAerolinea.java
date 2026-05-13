import vista.LoginFrame;
import utils.DatabaseConnection;

public class SistemaAerolinea {
    public static void main(String[] args) {
        // Probar conexión a BD primero
        DatabaseConnection.testConnection();
        
        // Establecer el Look and Feel del sistema
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la aplicación
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}