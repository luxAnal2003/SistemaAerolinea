package vista;

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

    // ================= TABLA =================

    private JTable tablaVuelos;
    private DefaultTableModel modeloTabla;

    // ================= CAMPOS =================

    private JTextField txtCodigo;
    private JTextField txtAerolinea;
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JTextField txtFecha;
    private JTextField txtHoraS;
    private JTextField txtHoraL;
    private JTextField txtCupos;

    // ================= BUSQUEDA =================

    private JTextField txtBuscar;

    // ================= COMBO =================

    private JComboBox<String> cbEstado;

    // ================= CONSTRUCTOR =================

    public GestionVuelosFrame() {

        setTitle("Gestión de Vuelos - Panel Administrativo");

        setSize(1250, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        cargarDatosEnTabla();
    }

    // ================= COMPONENTES =================

    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.setBackground(new Color(240, 240, 240));

        // =========================================================
        // PANEL IZQUIERDO
        // =========================================================

        JPanel panelForm = new JPanel(new GridBagLayout());

        panelForm.setPreferredSize(new Dimension(330, 0));

        panelForm.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(52, 152, 219), 2),
                        "Datos del Vuelo"
                ));

        panelForm.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ================= CAMPOS =================

        txtCodigo = new JTextField();

        txtAerolinea = new JTextField();

        txtOrigen = new JTextField();

        txtDestino = new JTextField();

        txtFecha = new JTextField();

        txtHoraS = new JTextField();

        txtHoraL = new JTextField();

        txtCupos = new JTextField();

        cbEstado = new JComboBox<>(
                new String[]{
                    "Programado",
                    "En Vuelo",
                    "Aterrizado",
                    "Cancelado"
                });

        agregarCampo(panelForm, "Código:", txtCodigo, gbc, 0);

        agregarCampo(panelForm, "Aerolínea:", txtAerolinea, gbc, 1);

        agregarCampo(panelForm, "Origen:", txtOrigen, gbc, 2);

        agregarCampo(panelForm, "Destino:", txtDestino, gbc, 3);

        agregarCampo(panelForm, "Fecha:", txtFecha, gbc, 4);

        agregarCampo(panelForm, "Hora Salida:", txtHoraS, gbc, 5);

        agregarCampo(panelForm, "Hora Llegada:", txtHoraL, gbc, 6);

        agregarCampo(panelForm, "Cupos:", txtCupos, gbc, 7);

        // ================= ESTADO =================

        gbc.gridx = 0;
        gbc.gridy = 8;

        JLabel lblEstado = new JLabel("Estado:");

        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panelForm.add(lblEstado, gbc);

        gbc.gridx = 1;

        panelForm.add(cbEstado, gbc);

        // =========================================================
        // BOTONES
        // =========================================================

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));

        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrar = crearBoton(
                "Registrar",
                new Color(46, 204, 113));

        JButton btnActualizar = crearBoton(
                "Actualizar",
                new Color(52, 152, 219));

        JButton btnCancelar = crearBoton(
                "Cancelar",
                new Color(231, 76, 60));

        JButton btnLimpiar = crearBoton(
                "Limpiar",
                new Color(241, 196, 15));

        JButton btnRecargar = crearBoton(
                "Recargar",
                new Color(155, 89, 182));

        JButton btnVolver = crearBoton(
                "Volver",
                new Color(127, 140, 141));

        panelBotones.add(btnRegistrar);

        panelBotones.add(btnActualizar);

        panelBotones.add(btnCancelar);

        panelBotones.add(btnLimpiar);

        panelBotones.add(btnRecargar);

        panelBotones.add(btnVolver);

        gbc.gridx = 0;

        gbc.gridy = 9;

        gbc.gridwidth = 2;

        panelForm.add(panelBotones, gbc);

        // =========================================================
        // PANEL DERECHO
        // =========================================================

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));

        panelDerecho.setBackground(Color.WHITE);

        // ================= BUSQUEDA =================

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelBusqueda.setBackground(Color.WHITE);

        panelBusqueda.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(52, 152, 219), 2),
                        "Buscar Vuelos"
                ));

        txtBuscar = new JTextField(20);

        JButton btnBuscar = crearBoton(
                "Buscar",
                new Color(230, 126, 34));

        JButton btnMostrarTodos = crearBoton(
                "Mostrar Todos",
                new Color(52, 73, 94));

        panelBusqueda.add(
                new JLabel("Código o Aerolínea:"));

        panelBusqueda.add(txtBuscar);

        panelBusqueda.add(btnBuscar);

        panelBusqueda.add(btnMostrarTodos);

        // ================= TABLA =================

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
                    "Estado"
                }, 0);

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

        tablaVuelos.setSelectionForeground(Color.WHITE);

        // ================= CENTRAR TABLA =================

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tablaVuelos.getColumnCount(); i++) {

            tablaVuelos.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(center);
        }

        JScrollPane scrollPane =
                new JScrollPane(tablaVuelos);

        // ================= AGREGAR DERECHO =================

        panelDerecho.add(panelBusqueda, BorderLayout.NORTH);

        panelDerecho.add(scrollPane, BorderLayout.CENTER);

        // ================= MAIN =================

        mainPanel.add(panelForm, BorderLayout.WEST);

        mainPanel.add(panelDerecho, BorderLayout.CENTER);

        add(mainPanel);

        // =========================================================
        // EVENTOS
        // =========================================================

        btnRegistrar.addActionListener(
                e -> registrarVuelo());

        btnActualizar.addActionListener(
                e -> actualizarVuelo());

        btnCancelar.addActionListener(
                e -> cancelarVuelo());

        btnLimpiar.addActionListener(
                e -> limpiarCampos());

        btnRecargar.addActionListener(
                e -> cargarDatosEnTabla());

        btnBuscar.addActionListener(
                e -> buscarVuelo());

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

    // =========================================================
    // CREAR BOTONES
    // =========================================================

    private JButton crearBoton(String texto, Color color) {

        JButton btn = new JButton(texto);

        btn.setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        btn.setBackground(color);

        btn.setForeground(Color.WHITE);

        btn.setFocusPainted(false);

        btn.setBorderPainted(false);

        btn.setOpaque(true);

        btn.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // =========================================================
    // AGREGAR CAMPOS
    // =========================================================

    private void agregarCampo(
            JPanel panel,
            String texto,
            JTextField campo,
            GridBagConstraints gbc,
            int y) {

        gbc.gridx = 0;

        gbc.gridy = y;

        JLabel lbl = new JLabel(texto);

        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panel.add(lbl, gbc);

        gbc.gridx = 1;

        campo.setPreferredSize(new Dimension(180, 30));

        panel.add(campo, gbc);
    }

    // =========================================================
    // REGISTRAR
    // =========================================================

    private void registrarVuelo() {

        try {

            Vuelo v = new Vuelo(
                    txtCodigo.getText(),
                    txtAerolinea.getText(),
                    txtOrigen.getText(),
                    txtDestino.getText(),
                    txtFecha.getText(),
                    txtHoraS.getText(),
                    txtHoraL.getText(),
                    Integer.parseInt(txtCupos.getText()),
                    cbEstado.getSelectedItem().toString()
            );

            if (registrar(v)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vuelo registrado");

                cargarDatosEnTabla();

                limpiarCampos();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error en datos");
        }
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    private void actualizarVuelo() {

        try {

            Vuelo v = new Vuelo(
                    txtCodigo.getText(),
                    txtAerolinea.getText(),
                    txtOrigen.getText(),
                    txtDestino.getText(),
                    txtFecha.getText(),
                    txtHoraS.getText(),
                    txtHoraL.getText(),
                    Integer.parseInt(txtCupos.getText()),
                    cbEstado.getSelectedItem().toString()
            );

            if (actualizar(v)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vuelo actualizado");

                cargarDatosEnTabla();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar");
        }
    }

    // =========================================================
    // CANCELAR
    // =========================================================

    private void cancelarVuelo() {

        String codigo = txtCodigo.getText();

        if (codigo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un vuelo");

            return;
        }

        if (cancelar(codigo)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vuelo cancelado");

            cargarDatosEnTabla();
        }
    }

    // =========================================================
    // BUSCAR
    // =========================================================

    private void buscarVuelo() {

        String texto = txtBuscar.getText();

        modeloTabla.setRowCount(0);

        String sql =
                "SELECT * FROM vuelos "
                + "WHERE codigo LIKE ? "
                + "OR aerolinea LIKE ?";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, "%" + texto + "%");

            ps.setString(2, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modeloTabla.addRow(new Object[]{
                    rs.getString("codigo"),
                    rs.getString("aerolinea"),
                    rs.getString("origen"),
                    rs.getString("destino"),
                    rs.getString("fecha_salida"),
                    rs.getString("hora_salida"),
                    rs.getString("hora_llegada"),
                    rs.getInt("cupos"),
                    rs.getString("estado")
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error búsqueda");
        }
    }

    // =========================================================
    // LLENAR CAMPOS
    // =========================================================

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
    }

    // =========================================================
    // LIMPIAR
    // =========================================================

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
    }

    // =========================================================
    // CARGAR TABLA
    // =========================================================

    private void cargarDatosEnTabla() {

        modeloTabla.setRowCount(0);

        List<Vuelo> lista = obtenerTodos();

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
                v.getEstado()
            });
        }
    }

    // =========================================================
    // SQL REGISTRAR
    // =========================================================

    public boolean registrar(Vuelo v) {

        String sql =
                "INSERT INTO vuelos VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, v.getCodigo());
            ps.setString(2, v.getAerolinea());
            ps.setString(3, v.getOrigen());
            ps.setString(4, v.getDestino());
            ps.setString(5, v.getFechaSalida());
            ps.setString(6, v.getHoraSalida());
            ps.setString(7, v.getHoraLlegada());
            ps.setInt(8, v.getCupos());
            ps.setString(9, v.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            return false;
        }
    }

    // =========================================================
    // SQL ACTUALIZAR
    // =========================================================

    public boolean actualizar(Vuelo v) {

        String sql =
                "UPDATE vuelos SET aerolinea=?, "
                + "origen=?, destino=?, fecha_salida=?, "
                + "hora_salida=?, hora_llegada=?, "
                + "cupos=?, estado=? WHERE codigo=?";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, v.getAerolinea());
            ps.setString(2, v.getOrigen());
            ps.setString(3, v.getDestino());
            ps.setString(4, v.getFechaSalida());
            ps.setString(5, v.getHoraSalida());
            ps.setString(6, v.getHoraLlegada());
            ps.setInt(7, v.getCupos());
            ps.setString(8, v.getEstado());
            ps.setString(9, v.getCodigo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            return false;
        }
    }

    // =========================================================
    // SQL CANCELAR
    // =========================================================

    public boolean cancelar(String codigo) {

        String sql =
                "UPDATE vuelos SET estado='Cancelado' "
                + "WHERE codigo=?";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, codigo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            return false;
        }
    }

    // =========================================================
    // OBTENER TODOS
    // =========================================================

    public List<Vuelo> obtenerTodos() {

        List<Vuelo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vuelos";

        try (
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {

                lista.add(new Vuelo(
                        rs.getString("codigo"),
                        rs.getString("aerolinea"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("fecha_salida"),
                        rs.getString("hora_salida"),
                        rs.getString("hora_llegada"),
                        rs.getInt("cupos"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar vuelos");
        }

        return lista;
    }
}