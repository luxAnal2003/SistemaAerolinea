package vista;

import controlador.ClienteController;
import modelo.Cliente;
import utils.Validator;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private ClienteController clienteController;
    
    private JTextField txtCedula;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtCelular;
    
    public RegisterFrame() {
        clienteController = new ClienteController();
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Registro de Nuevo Cliente - Sistema de Vuelos");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Panel superior
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(33, 150, 243));
        topPanel.setPreferredSize(new Dimension(500, 80));
        
        JLabel lblTitulo = new JLabel("Registro de Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Cédula
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblCedula = new JLabel("Número de Cédula:*");
        lblCedula.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblCedula, gbc);
        
        gbc.gridx = 1;
        txtCedula = new JTextField(20);
        txtCedula.setToolTipText("10 dígitos numéricos");
        formPanel.add(txtCedula, gbc);
        
        // Nombres
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblNombres = new JLabel("Nombres:*");
        lblNombres.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblNombres, gbc);
        
        gbc.gridx = 1;
        txtNombres = new JTextField(20);
        txtNombres.setToolTipText("Solo letras y espacios, máx 100 caracteres");
        formPanel.add(txtNombres, gbc);
        
        // Apellidos
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblApellidos = new JLabel("Apellidos:*");
        lblApellidos.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblApellidos, gbc);
        
        gbc.gridx = 1;
        txtApellidos = new JTextField(20);
        txtApellidos.setToolTipText("Solo letras y espacios, máx 100 caracteres");
        formPanel.add(txtApellidos, gbc);
        
        // Email
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblEmail = new JLabel("Correo Electrónico:*");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        txtEmail.setToolTipText("usuario@dominio.com");
        formPanel.add(txtEmail, gbc);
        
        // Contraseña
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblPassword = new JLabel("Contraseña:*");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        txtPassword.setToolTipText("8-15 caracteres, al menos una mayúscula y un número");
        formPanel.add(txtPassword, gbc);
        
        // Confirmar Contraseña
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblConfirmPassword = new JLabel("Confirmar Contraseña:*");
        lblConfirmPassword.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblConfirmPassword, gbc);
        
        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField(20);
        formPanel.add(txtConfirmPassword, gbc);
        
        // Celular
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblCelular = new JLabel("Número de Celular:*");
        lblCelular.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblCelular, gbc);
        
        gbc.gridx = 1;
        txtCelular = new JTextField(20);
        txtCelular.setToolTipText("9 dígitos numéricos exactos");
        formPanel.add(txtCelular, gbc);
        
        // Requisitos de contraseña
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JPanel reqPanel = new JPanel();
        reqPanel.setBackground(new Color(255, 243, 224));
        reqPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 152, 0)));
        
        JLabel lblReqs = new JLabel("<html><font size='2'>🔒 Requisitos:<br>• 8 a 15 caracteres<br>• Al menos una mayúscula<br>• Al menos un número</font></html>");
        reqPanel.add(lblReqs);
        formPanel.add(reqPanel, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBackground(new Color(76, 175, 80));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setPreferredSize(new Dimension(150, 40));
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.addActionListener(e -> registrarCliente());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(100, 100, 100));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(150, 40));
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> cancelar());
        
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void registrarCliente() {
        // Obtener datos
        String cedula = txtCedula.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String celular = txtCelular.getText().trim();
        
        // Validar campos vacíos
        if (cedula.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || 
            email.isEmpty() || password.isEmpty() || celular.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todos los campos obligatorios (*)", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar cédula
        if (!Validator.validarCedula(cedula)) {
            JOptionPane.showMessageDialog(this, 
                "La cédula debe contener exactamente 10 dígitos numéricos", 
                "Error en cédula", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si cédula ya existe
        if (clienteController.cedulaExiste(cedula)) {
            JOptionPane.showMessageDialog(this, 
                "Ya existe un cliente registrado con esta cédula", 
                "Cédula duplicada", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar nombres
        if (!Validator.validarNombre(nombres)) {
            JOptionPane.showMessageDialog(this, 
                "Los nombres solo pueden contener letras y espacios (máx 100 caracteres)", 
                "Error en nombres", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar apellidos
        if (!Validator.validarNombre(apellidos)) {
            JOptionPane.showMessageDialog(this, 
                "Los apellidos solo pueden contener letras y espacios (máx 100 caracteres)", 
                "Error en apellidos", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar email
        if (!Validator.validarEmail(email)) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese un correo electrónico válido (ejemplo: usuario@dominio.com)", 
                "Error en email", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si email ya existe
        if (clienteController.emailExiste(email)) {
            JOptionPane.showMessageDialog(this, 
                "Ya existe un cliente registrado con este correo electrónico", 
                "Email duplicado", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar contraseña
        if (!Validator.validarPassword(password)) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña debe tener entre 8 y 15 caracteres,\n" +
                "incluir al menos una letra mayúscula y un número", 
                "Error en contraseña", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmar contraseña
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Las contraseñas no coinciden", 
                "Error de confirmación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar celular
        if (!Validator.validarCelular(celular)) {
            JOptionPane.showMessageDialog(this, 
                "El número de celular debe tener exactamente 9 dígitos numéricos", 
                "Error en celular", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear cliente
        Cliente nuevoCliente = new Cliente(cedula, nombres, apellidos, email, password, celular);
        
        if (clienteController.crearCliente(nuevoCliente)) {
            JOptionPane.showMessageDialog(this, 
                "¡Registro exitoso! Ya puedes iniciar sesión", 
                "Registro completado", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Volver al login
            new LoginFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el cliente. Verifique los datos e intente nuevamente.", 
                "Error de registro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cancelar() {
        new LoginFrame().setVisible(true);
        this.dispose();
    }
}