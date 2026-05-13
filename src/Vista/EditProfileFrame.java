package vista;

import controlador.LoginController;
import controlador.ClienteController;
import modelo.Cliente;
import utils.Validator;

import javax.swing.*;
import java.awt.*;

public class EditProfileFrame extends JFrame {
    private Cliente cliente;
    private ClienteController clienteController;
    
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtCelular;
    private JPasswordField txtPasswordActual;
    
    public EditProfileFrame() {
        clienteController = new ClienteController();
        this.cliente = LoginController.getClienteActual();
        initComponents();
        cargarDatos();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Editar Perfil - Sistema de Vuelos");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Panel superior
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(33, 150, 243));
        topPanel.setPreferredSize(new Dimension(500, 70));
        
        JLabel lblTitulo = new JLabel("Editar Mi Perfil");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Contraseña Actual 
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblPassActual = new JLabel("Contraseña Actual:*");
        lblPassActual.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblPassActual, gbc);
        
        gbc.gridx = 1;
        txtPasswordActual = new JPasswordField(20);
        formPanel.add(txtPasswordActual, gbc);
        
        // Separador
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JSeparator separator = new JSeparator();
        formPanel.add(separator, gbc);
        
        // Nombres
        row++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblNombres = new JLabel("Nombres:*");
        lblNombres.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblNombres, gbc);
        
        gbc.gridx = 1;
        txtNombres = new JTextField(20);
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
        formPanel.add(txtEmail, gbc);
        
        // Nueva Contraseña
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblPassword = new JLabel("Nueva Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        formPanel.add(txtPassword, gbc);
        
        // Confirmar Contraseña
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblConfirmPassword = new JLabel("Confirmar Contraseña:");
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
        formPanel.add(txtCelular, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setPreferredSize(new Dimension(150, 40));
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(e -> guardarCambios());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(100, 100, 100));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(150, 40));
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> cancelar());
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarDatos() {
        txtNombres.setText(cliente.getNombres());
        txtApellidos.setText(cliente.getApellidos());
        txtEmail.setText(cliente.getEmail());
        txtCelular.setText(cliente.getCelular());
    }
    
    private void guardarCambios() {
        // Validar contraseña actual
        String passwordActual = new String(txtPasswordActual.getPassword());
        if (!cliente.getPassword().equals(passwordActual)) {
            JOptionPane.showMessageDialog(this, 
                "Contraseña actual incorrecta", 
                "Error de seguridad", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Obtener datos
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String email = txtEmail.getText().trim();
        String celular = txtCelular.getText().trim();
        String nuevaPassword = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        // Validaciones
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || celular.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todos los campos obligatorios (*)", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!Validator.validarNombre(nombres)) {
            JOptionPane.showMessageDialog(this, 
                "Los nombres solo pueden contener letras y espacios (máx 100 caracteres)", 
                "Error en nombres", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!Validator.validarNombre(apellidos)) {
            JOptionPane.showMessageDialog(this, 
                "Los apellidos solo pueden contener letras y espacios (máx 100 caracteres)", 
                "Error en apellidos", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!Validator.validarEmail(email)) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese un correo electrónico válido (máx 100 caracteres)", 
                "Error en email", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si el email ya existe en otro cliente
        if (!email.equals(cliente.getEmail()) && clienteController.emailExiste(email)) {
            JOptionPane.showMessageDialog(this, 
                "El correo electrónico ya está registrado por otro usuario", 
                "Email duplicado", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!Validator.validarCelular(celular)) {
            JOptionPane.showMessageDialog(this, 
                "El número de celular debe tener exactamente 9 dígitos", 
                "Error en celular", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar nueva contraseña 
        if (!nuevaPassword.isEmpty()) {
            if (!Validator.validarPassword(nuevaPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "La contraseña debe tener entre 8 y 15 caracteres,\n" +
                    "incluir al menos una letra mayúscula y un número", 
                    "Error en contraseña", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!nuevaPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "Las contraseñas nuevas no coinciden", 
                    "Error en validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            nuevaPassword = cliente.getPassword();
        }
        
        // Actualizar cliente
        cliente.setNombres(nombres);
        cliente.setApellidos(apellidos);
        cliente.setEmail(email);
        cliente.setPassword(nuevaPassword);
        cliente.setCelular(celular);
        
        if (clienteController.actualizarCliente(cliente)) {
            LoginController.setClienteActual(cliente);
            JOptionPane.showMessageDialog(this, 
                "Perfil actualizado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            new ProfileFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar el perfil", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cancelar() {
        new ProfileFrame().setVisible(true);
        this.dispose();
    }
}