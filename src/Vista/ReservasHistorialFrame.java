package vista;

import controlador.LoginController;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReservasHistorialFrame extends JFrame {
    private Cliente cliente;
    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    
    public ReservasHistorialFrame() {
        this.cliente = LoginController.getClienteActual();
        initComponents();
        cargarDatosSimulados();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Historial de Reservas - Sistema de Vuelos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Panel superior
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(33, 150, 243));
        topPanel.setPreferredSize(new Dimension(800, 80));
        
        JLabel lblTitulo = new JLabel("Historial de Reservas - " + cliente.getNombreCompleto());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
        
        // Crear la tabla
        String[] columnas = {"ID", "Destino", "Fecha de Vuelo", "Hora", "Estado", "Fecha de Reserva"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaReservas.setRowHeight(25);
        tablaReservas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(255, 243, 224));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblInfo = new JLabel("ℹ️ Los datos de reservas aparecerán cuando el módulo de Reservas esté implementado");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(255, 152, 0));
        infoPanel.add(lblInfo);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(33, 150, 243));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setPreferredSize(new Dimension(120, 35));
        btnActualizar.setBorderPainted(false);
        btnActualizar.addActionListener(e -> cargarDatosSimulados());
        
        JButton btnVolver = new JButton("Volver a Mi Perfil");
        btnVolver.setBackground(new Color(100, 100, 100));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setPreferredSize(new Dimension(150, 35));
        btnVolver.setBorderPainted(false);
        btnVolver.addActionListener(e -> volver());
        
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnVolver);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarDatosSimulados() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        Object[][] datosSimulados = {
            {1, "Quito - Guayaquil", "2026-06-15", "08:30 AM", "Pendiente", "2026-05-01"},
            {2, "Guayaquil - Madrid", "2025-12-10", "10:00 PM", "Completada", "2025-10-15"},
            {3, "Quito - New York", "2026-08-20", "02:15 PM", "Confirmada", "2026-05-20"},
            {4, "Cuenca - Miami", "2025-11-05", "11:45 AM", "Cancelada", "2025-09-10"}
        };
        
        for (Object[] fila : datosSimulados) {
            modeloTabla.addRow(fila);
        }
        
        // Mostrar mensaje informativo
        JOptionPane.showMessageDialog(this, 
            "Estos son datos de ejemplo (simulados).\n" +
            "Cuando tu compañero implemente el módulo de Reservas,\n" +
            "aquí aparecerán las reservas reales desde la base de datos.", 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void volver() {
        new ProfileFrame().setVisible(true);
        this.dispose();
    }
}