package vista;

import controlador.ClienteController;
import controlador.LoginController;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private ClienteController clienteController;
    
    public LoginFrame() {
        clienteController = new ClienteController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Sistema de Vuelos - Iniciar Sesión");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con color de fondo
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(33, 150, 243));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Panel del formulario (blanco)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(8, 8, 8, 8);
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(33, 150, 243));
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.gridwidth = 2;
        formPanel.add(lblTitulo, formGbc);
        
        // Espacio
        formGbc.gridy = 1;
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        
        // Email
        formGbc.gridy = 2;
        formGbc.gridwidth = 1;
        JLabel lblEmail = new JLabel("Correo electrónico:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(lblEmail, formGbc);
        
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        formGbc.gridx = 1;
        formPanel.add(txtEmail, formGbc);
        
        // Contraseña
        formGbc.gridx = 0;
        formGbc.gridy = 3;
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(lblPassword, formGbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        formGbc.gridx = 1;
        formPanel.add(txtPassword, formGbc);
        
        // Botones
        formGbc.gridy = 4;
        formGbc.gridx = 0;
        formGbc.gridwidth = 2;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(33, 150, 243));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.addActionListener(e -> iniciarSesion());
        
        JButton btnRegister = new JButton("Registrarse");
        btnRegister.setBackground(new Color(100, 100, 100));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setBorderPainted(false);
        btnRegister.setFocusPainted(false);
        btnRegister.setPreferredSize(new Dimension(150, 40));
        btnRegister.addActionListener(e -> abrirRegistro());
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);
        formPanel.add(buttonPanel, formGbc);
        
        // Agregar panel del formulario al panel principal
        mainPanel.add(formPanel);
        
        // Agregar todo al frame
        add(mainPanel);
    }
    
    private void iniciarSesion() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese su correo y contraseña", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Cliente cliente = clienteController.validarLogin(email, password);
        
        if (cliente != null) {
            LoginController.setClienteActual(cliente);
            JOptionPane.showMessageDialog(this, 
                "¡Bienvenido " + cliente.getNombreCompleto() + "!", 
                "Login exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir menú principal
            new MainMenuFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Correo o contraseña incorrectos", 
                "Error de login", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirRegistro() {
        new RegisterFrame().setVisible(true);
        this.dispose();
    }
}