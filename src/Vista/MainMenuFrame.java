package vista;

import controlador.LoginController;

import javax.swing.*;
import java.awt.*;
import Vista.FrmDashboard;

public class MainMenuFrame extends JFrame {
    
    public MainMenuFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Sistema de Vuelos - Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Panel superior con saludo
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(33, 150, 243));
        topPanel.setPreferredSize(new Dimension(800, 80));
        
        JLabel lblBienvenido = new JLabel("¡Bienvenido " + 
            LoginController.getClienteActual().getNombreCompleto() + "!");
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 20));
        lblBienvenido.setForeground(Color.WHITE);
        topPanel.add(lblBienvenido);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // Botón Mi Perfil (tu módulo)
        JButton btnPerfil = crearBoton("Mi Perfil", "perfil.png", new Color(33, 150, 243));
        btnPerfil.addActionListener(e -> abrirPerfil());
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(btnPerfil, gbc);
        
        JButton btnAeronave = crearBoton(
                "Aeronaves",
                "avion.png",
                new Color(0, 150, 136)
        );

        btnAeronave.addActionListener(
                e -> abrirAeronaves()
        );

        gbc.gridx = 1;

        buttonPanel.add(btnAeronave, gbc);
        
        // Botones para otros módulos (vacíos para tus compañeros)
//        JButton btnVuelos = crearBoton("Gestión de Vuelos", "vuelos.png", new Color(156, 39, 176));
//        btnVuelos.addActionListener(e -> JOptionPane.showMessageDialog(this, 
//            "Módulo de Vuelos - Por implementar", "Próximamente", JOptionPane.INFORMATION_MESSAGE));
//        gbc.gridx = 1;
//        buttonPanel.add(btnVuelos, gbc);
        
        JButton btnReservas = crearBoton("Mis Reservas", "reservas.png", new Color(255, 152, 0));
        btnReservas.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Módulo de Reservas - Por implementar", "Próximamente", JOptionPane.INFORMATION_MESSAGE));
        gbc.gridx = 2;
        buttonPanel.add(btnReservas, gbc);
        
        JButton btnPagos = crearBoton("Pagos", "pagos.png", new Color(76, 175, 80));
        btnPagos.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Módulo de Pagos - Por implementar", "Próximamente", JOptionPane.INFORMATION_MESSAGE));
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(btnPagos, gbc);
        
        JButton btnReportes = crearBoton("Reportes", "reportes.png", new Color(244, 67, 54));
        btnReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Módulo de Reportes - Por implementar", "Próximamente", JOptionPane.INFORMATION_MESSAGE));
        gbc.gridx = 1;
        buttonPanel.add(btnReportes, gbc);
        
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión", "salir.png", new Color(120, 120, 120));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        gbc.gridx = 2;
        buttonPanel.add(btnCerrarSesion, gbc);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JButton crearBoton(String texto, String icono, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(200, 120));
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
    
    private void abrirPerfil() {
        new ProfileFrame().setVisible(true);
        this.dispose();
    }
    
    private void abrirAeronaves() {
        new FrmDashboard().setVisible(true);
        this.dispose();
    }
    
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea cerrar sesión?", 
            "Cerrar Sesión", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            LoginController.cerrarSesion();
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}