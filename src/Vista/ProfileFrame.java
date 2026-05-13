package vista;

import controlador.LoginController;
import controlador.ClienteController;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {
    private Cliente cliente;
    private ClienteController clienteController;
    
    private JLabel lblCedula;
    private JLabel lblNombres;
    private JLabel lblApellidos;
    private JLabel lblEmail;
    private JLabel lblCelular;
    private JLabel lblFechaRegistro;
    private JLabel lblFechaModificacion;
    
    public ProfileFrame() {
        clienteController = new ClienteController();
        this.cliente = LoginController.getClienteActual();
        initComponents();
        cargarDatos();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Mi Perfil - Sistema de Vuelos");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Panel superior
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(33, 150, 243));
        topPanel.setPreferredSize(new Dimension(550, 80));
        
        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
        
        // Panel de información
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Cédula
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCedulaTitulo = new JLabel("Número de Cédula:");
        lblCedulaTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(lblCedulaTitulo, gbc);
        
        gbc.gridx = 1;
        lblCedula = new JLabel("");
        lblCedula.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblCedula, gbc);
        
        // Nombres
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNombresTitulo = new JLabel("Nombres:");
        lblNombresTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(lblNombresTitulo, gbc);
        
        gbc.gridx = 1;
        lblNombres = new JLabel("");
        lblNombres.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblNombres, gbc);
        
        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblApellidosTitulo = new JLabel("Apellidos:");
        lblApellidosTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(lblApellidosTitulo, gbc);
        
        gbc.gridx = 1;
        lblApellidos = new JLabel("");
        lblApellidos.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblApellidos, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblEmailTitulo = new JLabel("Correo Electrónico:");
        lblEmailTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(lblEmailTitulo, gbc);
        
        gbc.gridx = 1;
        lblEmail = new JLabel("");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblEmail, gbc);
        
        // Celular
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblCelularTitulo = new JLabel("Número de Celular:");
        lblCelularTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(lblCelularTitulo, gbc);
        
        gbc.gridx = 1;
        lblCelular = new JLabel("");
        lblCelular.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblCelular, gbc);
        
        // Fecha Registro
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblFechaRegTitulo = new JLabel("Fecha de Registro:");
        lblFechaRegTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(lblFechaRegTitulo, gbc);
        
        gbc.gridx = 1;
        lblFechaRegistro = new JLabel("");
        lblFechaRegistro.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblFechaRegistro, gbc);
        
        // Fecha Modificación
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel lblFechaModTitulo = new JLabel("Última Modificación:");
        lblFechaModTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(lblFechaModTitulo, gbc);
        
        gbc.gridx = 1;
        lblFechaModificacion = new JLabel("");
        lblFechaModificacion.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblFechaModificacion, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnEditar = new JButton("Editar Perfil");
        btnEditar.setBackground(new Color(33, 150, 243));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEditar.setPreferredSize(new Dimension(150, 40));
        btnEditar.setBorderPainted(false);
        btnEditar.addActionListener(e -> abrirEditarPerfil());
        
        JButton btnVolverMenu = new JButton("Volver al Menú");
        btnVolverMenu.setBackground(new Color(33, 150, 243));
        btnVolverMenu.setForeground(Color.WHITE);
        btnVolverMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolverMenu.setPreferredSize(new Dimension(150, 40));
        btnVolverMenu.setBorderPainted(false);
        btnVolverMenu.addActionListener(e -> volverAlMenu());
        buttonPanel.add(btnVolverMenu);
        
        JButton btnHistorial = new JButton("Ver Historial");
        btnHistorial.setBackground(new Color(255, 152, 0));
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFont(new Font("Arial", Font.BOLD, 14));
        btnHistorial.setPreferredSize(new Dimension(150, 40));
        btnHistorial.setBorderPainted(false);
        btnHistorial.addActionListener(e -> abrirHistorial());
        
        JButton btnEliminar = new JButton("Eliminar Cuenta");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setPreferredSize(new Dimension(150, 40));
        btnEliminar.setBorderPainted(false);
        btnEliminar.addActionListener(e -> abrirEliminarCuenta());
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(100, 100, 100));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setPreferredSize(new Dimension(150, 40));
        btnVolver.setBorderPainted(false);
        btnVolver.addActionListener(e -> volver());
        
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnHistorial);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnVolver);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarDatos() {
        // Recargar datos actualizados desde la base de datos
        cliente = clienteController.getClienteById(cliente.getId());
        LoginController.setClienteActual(cliente);
        
        lblCedula.setText(cliente.getCedula());
        lblNombres.setText(cliente.getNombres());
        lblApellidos.setText(cliente.getApellidos());
        lblEmail.setText(cliente.getEmail());
        lblCelular.setText(cliente.getCelular());
        
        if (cliente.getFechaRegistro() != null) {
            lblFechaRegistro.setText(cliente.getFechaRegistro().toString());
        }
        if (cliente.getFechaUltimaModificacion() != null) {
            lblFechaModificacion.setText(cliente.getFechaUltimaModificacion().toString());
        }
    }
    
    private void abrirEditarPerfil() {
        new EditProfileFrame().setVisible(true);
        this.dispose();
    }
    
    private void abrirHistorial() {
        new ReservasHistorialFrame().setVisible(true);
        this.dispose();
    }
    
    private void abrirEliminarCuenta() {
        new DeleteAccountFrame().setVisible(true);
        this.dispose();
    }
    
    private void volver() {
        new MainMenuFrame().setVisible(true);
        this.dispose();
    }
    
    private void volverAlMenu() {
    new MainMenuFrame().setVisible(true);
    this.dispose();
}
    
}