package vista;

import controlador.LoginController;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import Modelo.Reserva;
import controlador.ReservaController;
import java.util.List;

public class ReservasHistorialFrame extends JFrame {

    private Cliente cliente;
    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    Cliente clienteControll = LoginController.getClienteActual();

    public ReservasHistorialFrame() {
        this.cliente = LoginController.getClienteActual();
        initComponents();
        cargarReservas();
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
        String[] columnas = {"ID",
            "Ruta",
            "Fecha Reserva",
            "Pasajeros",
            "Total",
            "Estado",
            "Asiento"
        };
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
        btnActualizar.addActionListener(e -> cargarReservas());

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

    private void cargarReservas() {

        modeloTabla.setRowCount(0);

        ReservaController rc = new ReservaController();

        Cliente cliente = LoginController.getClienteActual();

        List<Reserva> lista = rc.listarReservas(
                cliente.getId()
        );

        for (Reserva r : lista) {

            modeloTabla.addRow(new Object[]{
                r.getIdReserva(),
                r.getRuta(),
                r.getFechaReserva(),
                r.getCantidadPasajeros(),
                r.getTotal(),
                r.getEstado(),
                r.getAsiento()
            });
        }
    }

    private void volver() {
        new ProfileFrame().setVisible(true);
        this.dispose();
    }
}
