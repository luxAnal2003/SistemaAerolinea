// ============================
// VISTA: GestionVuelosFrame.java
// ============================
package vista;

import Modelo.Aeronave;
import controlador.VueloController;
import modelo.Vuelo;
import utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionVuelosFrame extends JFrame {

    private JTable tablaVuelos;
    private DefaultTableModel modeloTabla;
    private VueloController vueloController;

    private JTextField txtCodigo;
    private JTextField txtAerolinea;
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JTextField txtFecha;
    private JTextField txtHoraS;
    private JTextField txtHoraL;
    private JTextField txtCupos;

    private JTextField txtBuscar;

    private JComboBox<String> cbEstado;
    private JComboBox<String> cbAeronave;

    private List<Aeronave> listaAeronaves;

    public GestionVuelosFrame() {

        vueloController = new VueloController();

        setTitle("Gestión de Vuelos");

        setSize(1250, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        cargarAeronaves();

        cargarDatosEnTabla();
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.setBackground(new Color(240, 240, 240));

        // =========================================
        // PANEL FORMULARIO
        // =========================================
        JPanel panelForm = new JPanel(new GridBagLayout());

        panelForm.setPreferredSize(new Dimension(340, 0));

        panelForm.setBackground(Color.WHITE);

        panelForm.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(52, 152, 219), 2),
                        "Datos del Vuelo"
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtCodigo = new JTextField();
        txtAerolinea = new JTextField();
        txtOrigen = new JTextField();
        txtDestino = new JTextField();
        txtFecha = new JTextField();
        txtHoraS = new JTextField();
        txtHoraL = new JTextField();
        txtCupos = new JTextField();

        cbEstado = new JComboBox<>(new String[]{
            "Programado",
            "En Vuelo",
            "Aterrizado",
            "Cancelado"
        });

        cbAeronave = new JComboBox<>();

        agregarCampo(panelForm, "Código:", txtCodigo, gbc, 0);
        agregarCampo(panelForm, "Aerolínea:", txtAerolinea, gbc, 1);
        agregarCampo(panelForm, "Origen:", txtOrigen, gbc, 2);
        agregarCampo(panelForm, "Destino:", txtDestino, gbc, 3);
        agregarCampo(panelForm, "Fecha:", txtFecha, gbc, 4);
        agregarCampo(panelForm, "Hora Salida:", txtHoraS, gbc, 5);
        agregarCampo(panelForm, "Hora Llegada:", txtHoraL, gbc, 6);
        agregarCampo(panelForm, "Cupos:", txtCupos, gbc, 7);

        // =========================================
        // AERONAVE
        // =========================================
        gbc.gridx = 0;
        gbc.gridy = 8;

        JLabel lblAeronave = new JLabel("Aeronave:");

        lblAeronave.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panelForm.add(lblAeronave, gbc);

        gbc.gridx = 1;

        panelForm.add(cbAeronave, gbc);

        // =========================================
        // ESTADO
        // =========================================
        gbc.gridx = 0;
        gbc.gridy = 9;

        JLabel lblEstado = new JLabel("Estado:");

        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panelForm.add(lblEstado, gbc);

        gbc.gridx = 1;

        panelForm.add(cbEstado, gbc);

        // =========================================
        // BOTONES
        // =========================================
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));

        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrar
                = crearBoton("Registrar",
                        new Color(46, 204, 113));

        JButton btnActualizar
                = crearBoton("Actualizar",
                        new Color(52, 152, 219));

        JButton btnCancelar
                = crearBoton("Cancelar",
                        new Color(231, 76, 60));

        JButton btnLimpiar
                = crearBoton("Limpiar",
                        new Color(241, 196, 15));

        JButton btnRecargar
                = crearBoton("Recargar",
                        new Color(155, 89, 182));

        JButton btnVolver
                = crearBoton("Volver",
                        new Color(127, 140, 141));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRecargar);
        panelBotones.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;

        panelForm.add(panelBotones, gbc);

        // =========================================
        // PANEL DERECHO
        // =========================================
        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));

        panelDerecho.setBackground(Color.WHITE);

        JPanel panelBusqueda
                = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelBusqueda.setBackground(Color.WHITE);

        panelBusqueda.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(52, 152, 219), 2),
                        "Buscar Vuelos"
                )
        );

        txtBuscar = new JTextField(20);

        JButton btnBuscar
                = crearBoton("Buscar",
                        new Color(230, 126, 34));

        JButton btnMostrarTodos
                = crearBoton("Mostrar Todos",
                        new Color(52, 73, 94));

        panelBusqueda.add(new JLabel("Código o Aerolínea:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);

        // =========================================
        // TABLA
        // =========================================
        modeloTabla = new DefaultTableModel(
                new Object[]{
                    "Código",
                    "Aerolínea",
                    "Origen",
                    "Destino",
                    "Fecha",
                    "Hora Salida",
                    "Hora Llegada",
                    "Cupos",
                    "Estado",
                    "ID Aeronave"
                }, 0
        );

        tablaVuelos = new JTable(modeloTabla);

        tablaVuelos.setRowHeight(28);

        tablaVuelos.setFont(
                new Font("Segoe UI", Font.PLAIN, 13));

        tablaVuelos.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        tablaVuelos.getTableHeader().setBackground(
                new Color(52, 73, 94));

        tablaVuelos.getTableHeader().setForeground(Color.WHITE);

        tablaVuelos.setSelectionBackground(
                new Color(52, 152, 219));

        DefaultTableCellRenderer center
                = new DefaultTableCellRenderer();

        center.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tablaVuelos.getColumnCount(); i++) {

            tablaVuelos.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(center);
        }

        JScrollPane scrollPane
                = new JScrollPane(tablaVuelos);

        panelDerecho.add(panelBusqueda, BorderLayout.NORTH);

        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(panelForm, BorderLayout.WEST);

        mainPanel.add(panelDerecho, BorderLayout.CENTER);

        add(mainPanel);

        // =========================================
        // EVENTOS
        // =========================================
        btnRegistrar.addActionListener(e -> registrarVuelo());

        btnActualizar.addActionListener(e -> actualizarVuelo());

        btnCancelar.addActionListener(e -> cancelarVuelo());

        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnRecargar.addActionListener(e -> cargarDatosEnTabla());

        btnBuscar.addActionListener(e -> buscarVuelo());

        btnMostrarTodos.addActionListener(
                e -> cargarDatosEnTabla());

        btnVolver.addActionListener(e -> {
            new MainMenuFrame().setVisible(true);
            dispose();
        });

        tablaVuelos.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila = tablaVuelos.getSelectedRow();

                    if (fila != -1) {

                        llenarCampos(fila);
                    }
                });
    }

    // =========================================
    // CARGAR AERONAVES
    // =========================================
    private void cargarAeronaves() {

        listaAeronaves = new ArrayList<>();

        cbAeronave.removeAllItems();

        String sql = "SELECT * FROM aeronaves";

        try (
                Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Aeronave a = new Aeronave();

                a.setIdAeronave(rs.getInt("id_aeronave"));
                a.setModelo(rs.getString("modelo"));

                listaAeronaves.add(a);

                cbAeronave.addItem(
                        a.getIdAeronave()
                        + " - "
                        + a.getModelo()
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error cargando aeronaves"
            );
        }
    }

    // =========================================
    // REGISTRAR
    // =========================================
    private void registrarVuelo() {

        try {

            int index = cbAeronave.getSelectedIndex();

            int idAeronave
                    = listaAeronaves.get(index).getIdAeronave();

            Vuelo v = new Vuelo(
                    txtCodigo.getText(),
                    txtAerolinea.getText(),
                    txtOrigen.getText(),
                    txtDestino.getText(),
                    txtFecha.getText(),
                    txtHoraS.getText(),
                    txtHoraL.getText(),
                    Integer.parseInt(txtCupos.getText()),
                    cbEstado.getSelectedItem().toString(),
                    idAeronave
            );

            boolean registrado = vueloController.registrar(v);

            JOptionPane.showMessageDialog(
                    this,
                    vueloController.getMensaje()
            );

            if (registrado) {

                cargarDatosEnTabla();

                limpiarCampos();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al registrar: " + e.getMessage()
            );
        }
    }

    // =========================================
    // ACTUALIZAR
    // =========================================
    private void actualizarVuelo() {

        try {

            int index = cbAeronave.getSelectedIndex();

            int idAeronave
                    = listaAeronaves.get(index).getIdAeronave();

            Vuelo v = new Vuelo(
                    txtCodigo.getText(),
                    txtAerolinea.getText(),
                    txtOrigen.getText(),
                    txtDestino.getText(),
                    txtFecha.getText(),
                    txtHoraS.getText(),
                    txtHoraL.getText(),
                    Integer.parseInt(txtCupos.getText()),
                    cbEstado.getSelectedItem().toString(),
                    idAeronave
            );

            boolean actualizado = vueloController.actualizar(v);

            JOptionPane.showMessageDialog(
                    this,
                    vueloController.getMensaje()
            );

            if (actualizado) {

                cargarDatosEnTabla();

                limpiarCampos();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar: " + e.getMessage()
            );
        }
    }

    // =========================================
    // CANCELAR
    // =========================================
    private void cancelarVuelo() {

        String codigo = txtCodigo.getText();

        if (vueloController.cancelar(codigo)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vuelo cancelado");

            cargarDatosEnTabla();
        }
    }

    // =========================================
    // BUSCAR
    // =========================================
    private void buscarVuelo() {

        modeloTabla.setRowCount(0);

        List<Vuelo> lista
                = vueloController.buscarVuelo(
                        txtBuscar.getText());

        for (Vuelo v : lista) {

            modeloTabla.addRow(new Object[]{
                v.getCodigo(),
                v.getAerolinea(),
                v.getOrigen(),
                v.getDestino(),
                v.getFechaSalida(),
                v.getHoraSalida(),
                v.getHoraLlegada(),
                v.getCupos(),
                v.getEstado(),
                v.getIdAeronave()
            });
        }
    }

    // =========================================
    // TABLA
    // =========================================
    private void cargarDatosEnTabla() {

        modeloTabla.setRowCount(0);

        List<Vuelo> lista = vueloController.listarVuelos();

        for (Vuelo v : lista) {

            modeloTabla.addRow(new Object[]{
                v.getCodigo(),
                v.getAerolinea(),
                v.getOrigen(),
                v.getDestino(),
                v.getFechaSalida(),
                v.getHoraSalida(),
                v.getHoraLlegada(),
                v.getCupos(),
                v.getEstado(),
                v.getIdAeronave()
            });
        }
    }

    // =========================================
    // LLENAR CAMPOS
    // =========================================
    private void llenarCampos(int fila) {

        txtCodigo.setText(
                modeloTabla.getValueAt(fila, 0).toString());

        txtAerolinea.setText(
                modeloTabla.getValueAt(fila, 1).toString());

        txtOrigen.setText(
                modeloTabla.getValueAt(fila, 2).toString());

        txtDestino.setText(
                modeloTabla.getValueAt(fila, 3).toString());

        txtFecha.setText(
                modeloTabla.getValueAt(fila, 4).toString());

        txtHoraS.setText(
                modeloTabla.getValueAt(fila, 5).toString());

        txtHoraL.setText(
                modeloTabla.getValueAt(fila, 6).toString());

        txtCupos.setText(
                modeloTabla.getValueAt(fila, 7).toString());

        cbEstado.setSelectedItem(
                modeloTabla.getValueAt(fila, 8).toString());

        int idAeronave = Integer.parseInt(
                modeloTabla.getValueAt(fila, 9).toString()
        );

        for (int i = 0; i < listaAeronaves.size(); i++) {

            if (listaAeronaves.get(i).getIdAeronave() == idAeronave) {

                cbAeronave.setSelectedIndex(i);

                break;
            }
        }
    }

    // =========================================
    // LIMPIAR
    // =========================================
    private void limpiarCampos() {

        txtCodigo.setText("");
        txtAerolinea.setText("");
        txtOrigen.setText("");
        txtDestino.setText("");
        txtFecha.setText("");
        txtHoraS.setText("");
        txtHoraL.setText("");
        txtCupos.setText("");

        cbEstado.setSelectedIndex(0);

        cbAeronave.setSelectedIndex(0);
    }

    // =========================================
    // BOTONES
    // =========================================
    private JButton crearBoton(String texto, Color color) {

        JButton btn = new JButton(texto);

        btn.setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        btn.setBackground(color);

        btn.setForeground(Color.WHITE);

        btn.setFocusPainted(false);

        btn.setBorderPainted(false);

        btn.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // =========================================
    // CAMPOS
    // =========================================
    private void agregarCampo(
            JPanel panel,
            String texto,
            JTextField campo,
            GridBagConstraints gbc,
            int y
    ) {

        gbc.gridx = 0;
        gbc.gridy = y;

        JLabel lbl = new JLabel(texto);

        lbl.setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        panel.add(lbl, gbc);

        gbc.gridx = 1;

        campo.setPreferredSize(
                new Dimension(180, 30));

        panel.add(campo, gbc);
    }
}
