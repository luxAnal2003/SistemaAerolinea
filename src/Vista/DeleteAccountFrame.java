package vista;

import controlador.LoginController;
import controlador.ClienteController;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;

public class DeleteAccountFrame extends JFrame {
    private Cliente cliente;
    private ClienteController clienteController;
    private JPasswordField txtPassword;
    
    public DeleteAccountFrame() {
        clienteController = new ClienteController();
        this.cliente = LoginController.getClienteActual();
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Eliminar Cuenta - Sistema de Vuelos");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Panel de advertencia
        JPanel warningPanel = new JPanel();
        warningPanel.setBackground(new Color(255, 235, 238));
        warningPanel.setBorder(BorderFactory.createLineBorder(new Color(244, 67, 54), 2));
        warningPanel.setLayout(new BorderLayout());
        warningPanel.setPreferredSize(new Dimension(380, 80));
        
        JLabel lblWarning = new JLabel("⚠️ ¡ADVERTENCIA! ⚠️");
        lblWarning.setFont(new Font("Arial", Font.BOLD, 16));
        lblWarning.setForeground(new Color(244, 67, 54));
        lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblMensaje = new JLabel("<html><center>Esta acción eliminará permanentemente tu cuenta.<br>No se puede deshacer.</center></html>");
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        warningPanel.add(lblWarning, BorderLayout.NORTH);
        warningPanel.add(lblMensaje, BorderLayout.CENTER);
        
        // Panel de confirmación
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(8, 8, 8, 8);
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        JLabel lblPassword = new JLabel("Ingrese su contraseña para confirmar:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblPassword, formGbc);
        
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtPassword, formGbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnEliminar = new JButton("Eliminar Mi Cuenta");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setPreferredSize(new Dimension(180, 40));
        btnEliminar.setBorderPainted(false);
        btnEliminar.addActionListener(e -> eliminarCuenta());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(100, 100, 100));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(180, 40));
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> cancelar());
        
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCancelar);
        
        // Agregar todo
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(warningPanel, gbc);
        
        gbc.gridy = 1;
        mainPanel.add(formPanel, gbc);
        
        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel);
    }
    
    private void eliminarCuenta() {
        String password = new String(txtPassword.getPassword());
        
        // Verificar contraseña
        if (!cliente.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, 
                "Contraseña incorrecta", 
                "Error de seguridad", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmación final
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está ABSOLUTAMENTE SEGURO de eliminar su cuenta?\n" +
            "Esta acción no se puede deshacer y perderá todos sus datos.", 
            "Confirmación Final", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Intentar eliminar
            boolean eliminado = clienteController.eliminarCliente(cliente.getId());
            
            if (eliminado) {
                JOptionPane.showMessageDialog(this, 
                    "Su cuenta ha sido eliminada correctamente", 
                    "Cuenta Eliminada", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Cerrar sesión y volver al login
                LoginController.cerrarSesion();
                new LoginFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se puede eliminar la cuenta porque tiene viajes pendientes.\n" +
                    "Por favor, cancele o complete sus reservas antes de eliminar su cuenta.", 
                    "No se puede eliminar", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelar() {
        new ProfileFrame().setVisible(true);
        this.dispose();
    }
}