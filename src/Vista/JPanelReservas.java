/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import controlador.ReservaController;
import Modelo.PasajeroExtra;
import Modelo.Reserva;
import controlador.LoginController;
import controlador.VueloController;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Vuelo;
import java.util.Collections;

/**
 *
 * @author admin
 */
public class JPanelReservas extends javax.swing.JPanel {

    private VueloController vueloController;
    Cliente cliente = LoginController.getClienteActual();
    private boolean cargandoCombos = true;
    private Vuelo vueloSeleccionado;

    private List<PasajeroExtra> pasajeros = new ArrayList<>();
    private ReservaController reservaController = new ReservaController();
    private JPanelAsientos panelAsientos;
    private int cantidadPasajeros = 1;

    /**
     * Creates new form JPanelAeronave
     */
    public JPanelReservas() {
        initComponents();
        vueloController = new VueloController();
        txtusuario.setText(" " + cliente.getNombres() + " " + cliente.getApellidos());
        cargarTarjeta();
        cargarCombos();
        cargarTablaVuelos(vueloController.listarVuelos());
        eventosCombos();
        buttonGroup1.add(rbtnAñadir);
        buttonGroup1.add(rbtnNoAñadir);

        rbtnNoAñadir.setSelected(true);

        txtNombresApellidos.setEnabled(false);
        txtCedula.setEnabled(false);
        txtFechaNacimiento.setEnabled(false);

        panelAsientos = new JPanelAsientos();
        jPaneAsientos.setLayout(new BorderLayout());
        jPaneAsientos.add(panelAsientos, BorderLayout.CENTER);

        txtCupos.setText("(Máx 0)");
        txtPrecioBase.setText("0.00");
        txtNumeroPasajeros.setText("1");
        txtTotalPagar.setText("0.00");

        eventosRadioButtons();
        eventosTabla();
    }

    private void cargarTarjeta() {
        txtNombreUsuarioTarjeta.setText(" " + cliente.getNombres() + " " + cliente.getApellidos());
        txtCedulaTarjeta.setText(cliente.getCedula());
    }

    private void cargarCombos() {

        cargandoCombos = true;

        cbxOrigen.removeAllItems();
        cbxDestino.removeAllItems();
        cbxFecha.removeAllItems();

        cbxOrigen.addItem("Seleccione");
        cbxDestino.addItem("Seleccione");
        cbxFecha.addItem("Seleccione");

        for (String origen : vueloController.obtenerOrigenes()) {
            cbxOrigen.addItem(origen);
        }

        for (String destino : vueloController.obtenerDestinos()) {
            cbxDestino.addItem(destino);
        }

        for (String fecha : vueloController.obtenerFechas()) {
            cbxFecha.addItem(fecha);
        }

        cargandoCombos = false;
    }

    private void eventosCombos() {

        cbxOrigen.addActionListener(e -> {
            if (!cargandoCombos) {
                filtrarVuelos();
            }
        });

        cbxDestino.addActionListener(e -> {
            if (!cargandoCombos) {
                filtrarVuelos();
            }
        });

        cbxFecha.addActionListener(e -> {
            if (!cargandoCombos) {
                filtrarVuelos();
            }
        });
    }

    private void filtrarVuelos() {

        String origen = cbxOrigen.getSelectedItem().toString();
        String destino = cbxDestino.getSelectedItem().toString();
        String fecha = cbxFecha.getSelectedItem().toString();

        List<Vuelo> lista = vueloController.filtrarVuelosFlex(
                origen, destino, fecha
        );

        if (lista.isEmpty()) {
            ((DefaultTableModel) tableVuelos.getModel()).setRowCount(0);

            JOptionPane.showMessageDialog(this,
                    "No hay vuelos con esos filtros");
        } else {
            cargarTablaVuelos(lista);
        }
    }

    private void cargarTablaVuelos(List<Vuelo> lista) {

        DefaultTableModel modelo
                = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Origen");
        modelo.addColumn("Destino");
        modelo.addColumn("Fecha");
        modelo.addColumn("Precio");
        modelo.addColumn("Cupos");

        for (Vuelo v : lista) {

            modelo.addRow(new Object[]{
                v.getIdVuelo(),
                v.getOrigen(),
                v.getDestino(),
                v.getFechaSalida(),
                v.getPrecioBase(),
                v.getCupos()
            });
        }

        tableVuelos.setModel(modelo);
    }

    private void eventosRadioButtons() {

        rbtnAñadir.addActionListener(e -> {

            txtNombresApellidos.setEnabled(true);
            txtCedula.setEnabled(true);
            txtFechaNacimiento.setEnabled(true);

            txtNombresApellidos.setEditable(true);
            txtCedula.setEditable(true);
            txtFechaNacimiento.setEditable(true);

            btnAniadirPasajero.setEnabled(true);
        });

        rbtnNoAñadir.addActionListener(e -> {

            txtNombresApellidos.setEnabled(false);
            txtCedula.setEnabled(false);
            txtFechaNacimiento.setEnabled(false);

            txtNombresApellidos.setText("");
            txtCedula.setText("");
            txtFechaNacimiento.setText("");
        });
    }

    private void eventosTabla() {

        tableVuelos.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting() && tableVuelos.getSelectedRow() != -1) {

                int fila = tableVuelos.getSelectedRow();

                vueloSeleccionado = new Vuelo();

                vueloSeleccionado.setIdVuelo(
                        Integer.parseInt(
                                tableVuelos.getValueAt(fila, 0).toString()
                        )
                );

                txtPrecioBase.setText(
                        tableVuelos.getValueAt(fila, 4).toString()
                );

                txtCupos.setText(
                        tableVuelos.getValueAt(fila, 5).toString()
                );

                calcularTotal();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtusuario = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableVuelos = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCupos = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNombresApellidos = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFechaNacimiento = new javax.swing.JTextField();
        rbtnAñadir = new javax.swing.JRadioButton();
        rbtnNoAñadir = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        jPaneAsientos = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreUsuarioTarjeta = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCedulaTarjeta = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtPrecioBase = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtNumeroPasajeros = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTotalPagar = new javax.swing.JLabel();
        btnComprar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cbxFecha = new javax.swing.JComboBox<>();
        cbxDestino = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cbxOrigen = new javax.swing.JComboBox<>();
        btnAniadirPasajero = new javax.swing.JButton();

        setBackground(new java.awt.Color(225, 238, 250));

        txtusuario.setBackground(new java.awt.Color(255, 255, 255));
        txtusuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile.png"))); // NOI18N
        txtusuario.setText("Usuario");

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel9.setText("GESTIÓN DE RESERVAS");
        jLabel9.setToolTipText("");

        jButton1.setBackground(new java.awt.Color(225, 238, 250));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"))); // NOI18N
        jButton1.setBorder(null);

        jSeparator2.setBackground(new java.awt.Color(0, 51, 102));
        jSeparator2.setForeground(new java.awt.Color(0, 51, 102));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel16.setText("1. Cliente Titular");
        jLabel16.setToolTipText("");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 20));

        tableVuelos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Salida", "Precio"
            }
        ));
        jScrollPane2.setViewportView(tableVuelos);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 230, 240));

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel11.setText("2. Selección de Vuelo");
        jLabel11.setToolTipText("");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 150, 20));

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel12.setText("5. Confirmación de Compra");
        jLabel12.setToolTipText("");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, 190, 20));

        txtCupos.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        txtCupos.setText("x");
        txtCupos.setToolTipText("");
        jPanel3.add(txtCupos, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 70, 20));

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel14.setText("Nombres y Apellidos");
        jLabel14.setToolTipText("");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 160, -1));

        txtNombresApellidos.setEditable(false);
        txtNombresApellidos.setEnabled(false);
        jPanel3.add(txtNombresApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 230, 30));

        txtCedula.setEditable(false);
        txtCedula.setEnabled(false);
        jPanel3.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 230, 30));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel10.setText("Fecha de Nacimiento (yyyy-mm-dd)");
        jLabel10.setToolTipText("");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 210, -1));

        txtFechaNacimiento.setEditable(false);
        txtFechaNacimiento.setEnabled(false);
        jPanel3.add(txtFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 230, 30));

        rbtnAñadir.setText("Añadir (+)");
        jPanel3.add(rbtnAñadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        rbtnNoAñadir.setText("No añadir (-)");
        jPanel3.add(rbtnNoAñadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, -1, -1));

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel15.setText("3. Registro de Pasajeros");
        jLabel15.setToolTipText("");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, 20));

        javax.swing.GroupLayout jPaneAsientosLayout = new javax.swing.GroupLayout(jPaneAsientos);
        jPaneAsientos.setLayout(jPaneAsientosLayout);
        jPaneAsientosLayout.setHorizontalGroup(
            jPaneAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        jPaneAsientosLayout.setVerticalGroup(
            jPaneAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        jPanel3.add(jPaneAsientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 320, 390));

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel17.setText("4. Seleccionar asientos");
        jLabel17.setToolTipText("");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, 20));

        jPanel1.setBackground(new java.awt.Color(241, 238, 238));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile.png"))); // NOI18N

        txtNombreUsuarioTarjeta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNombreUsuarioTarjeta.setText("NombreUsuario");

        jLabel4.setText("ID:");

        txtCedulaTarjeta.setText("xxxxxxxxxxxx");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombreUsuarioTarjeta)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCedulaTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNombreUsuarioTarjeta)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedulaTarjeta)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 230, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel8.setText("Resumen de Compra");
        jLabel8.setToolTipText("");

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 0, 10)); // NOI18N
        jLabel19.setText("Precio Base Vuelo:");
        jLabel19.setToolTipText("");

        txtPrecioBase.setFont(new java.awt.Font("Yu Gothic UI", 0, 10)); // NOI18N
        txtPrecioBase.setText("xxxxx");
        txtPrecioBase.setToolTipText("");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 10)); // NOI18N
        jLabel21.setText("Pasajeros:");
        jLabel21.setToolTipText("");

        txtNumeroPasajeros.setFont(new java.awt.Font("Yu Gothic UI", 0, 10)); // NOI18N
        txtNumeroPasajeros.setText("xxxxx");
        txtNumeroPasajeros.setToolTipText("");

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 1, 10)); // NOI18N
        jLabel23.setText("PRECIO TOTAL A PAGAR: ");
        jLabel23.setToolTipText("");

        txtTotalPagar.setFont(new java.awt.Font("Yu Gothic UI", 0, 10)); // NOI18N
        txtTotalPagar.setText("xxxxx");
        txtTotalPagar.setToolTipText("");

        btnComprar.setBackground(new java.awt.Color(0, 102, 204));
        btnComprar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnComprar.setForeground(new java.awt.Color(255, 255, 255));
        btnComprar.setText("Generar reserva y proceder al pago");
        btnComprar.setBorder(null);
        btnComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecioBase, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNumeroPasajeros, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTotalPagar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnComprar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtPrecioBase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumeroPasajeros)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtTotalPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComprar, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 230, 130));

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel18.setText("Cédula");
        jLabel18.setToolTipText("");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 160, -1));

        cbxFecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cbxFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 70, -1));

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cbxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 70, -1));

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel20.setText("Fecha");
        jLabel20.setToolTipText("");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 50, -1));

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel22.setText("Origen");
        jLabel22.setToolTipText("");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 50, -1));

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel24.setText("Destino");
        jLabel24.setToolTipText("");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 50, -1));

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cbxOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 70, -1));

        btnAniadirPasajero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAniadirPasajero.setForeground(new java.awt.Color(0, 102, 204));
        btnAniadirPasajero.setText("Añadir pasajero");
        btnAniadirPasajero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniadirPasajeroActionPerformed(evt);
            }
        });
        jPanel3.add(btnAniadirPasajero, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 130, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtusuario))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprarActionPerformed
        try {
            if (vueloSeleccionado == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un vuelo"
                );
                return;
            }

            List<String> asientos = panelAsientos.getAsientosSeleccionados();
            if (asientos.size() != cantidadPasajeros) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar exactamente "
                        + cantidadPasajeros
                        + " asientos"
                );
                return;
            }

            List<String> personas = new ArrayList<>();

            personas.add(cliente.getNombres() + " " + cliente.getApellidos());

            for (PasajeroExtra p : pasajeros) {
                personas.add(p.getNombre());
            }

            Collections.shuffle(asientos);

            StringBuilder detalle = new StringBuilder();
            detalle.append("ASIENTOS ASIGNADOS\n\n");

            for (int i = 0; i < personas.size(); i++) {

                String persona = personas.get(i);
                String asiento = asientos.get(i);

                detalle.append(persona)
                        .append(" -> Asiento ")
                        .append(asiento)
                        .append("\n");
            }

            double precio = Double.parseDouble(
                    txtPrecioBase.getText()
            );

            double total = precio * cantidadPasajeros;

            Reserva r = new Reserva();

            r.setIdCliente(cliente.getId());

            r.setIdVuelo(
                    vueloSeleccionado.getIdVuelo()
            );

            r.setCantidadPasajeros(cantidadPasajeros);

            r.setTotal(total);

            int idReserva = reservaController.crearReserva(r);

            if (idReserva > 0) {

                String asientoTitular = asientos.get(0);

                reservaController.actualizarAsientoTitular(
                        idReserva,
                        asientoTitular
                );

                for (int i = 0; i < pasajeros.size(); i++) {

                    PasajeroExtra p = pasajeros.get(i);

                    String asiento = asientos.get(i + 1);

                    reservaController.actualizarAsientoPasajero(
                            p.getIdentificacion(),
                            asiento
                    );
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Reserva creada correctamente\n"
                        + "Total pagado: $" + total
                        + "\n\n"
                        + detalle.toString()
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo crear la reserva"
                );
            }
        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al generar reserva"
            );
        }
    }//GEN-LAST:event_btnComprarActionPerformed

    private void btnAniadirPasajeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniadirPasajeroActionPerformed
        if (vueloSeleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un vuelo"
            );
            return;
        }

        String nombres = txtNombresApellidos.getText().trim();
        String cedula = txtCedula.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();

        if (nombres.isEmpty()
                || cedula.isEmpty()
                || fechaNacimiento.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos"
            );
            return;
        }
        if (!cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cédula debe contener exactamente 10 números"
            );
            return;
        }

        if (!fechaNacimiento.matches("\\d{4}-\\d{2}-\\d{2}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha debe tener formato YYYY-MM-DD\nEjemplo: 2000-05-14"
            );
            return;
        }
        PasajeroExtra p = new PasajeroExtra();

        p.setNombre(nombres);
        p.setIdentificacion(cedula);
        p.setFechaNacimiento(fechaNacimiento);

        boolean registrado = reservaController.guardarPasajero(
                p,
                cliente.getId(),
                vueloSeleccionado.getIdVuelo()
        );

        if (registrado) {
            pasajeros.add(p);
            JOptionPane.showMessageDialog(
                    this,
                    "Pasajero añadido correctamente"
            );

            cantidadPasajeros = pasajeros.size() + 1;

            txtNumeroPasajeros.setText(
                    String.valueOf(cantidadPasajeros)
            );

            calcularTotal();

            setear();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al registrar pasajero"
            );
        }
    }//GEN-LAST:event_btnAniadirPasajeroActionPerformed

    private void setear() {
        txtNombresApellidos.setText("");
        txtFechaNacimiento.setText("");
        txtCedula.setText("");
    }

    private void calcularTotal() {

        try {

            double precio = Double.parseDouble(
                    txtPrecioBase.getText()
            );

            double total = precio * cantidadPasajeros;

            txtTotalPagar.setText(
                    String.format("%.2f", total)
            );

        } catch (Exception e) {

            txtTotalPagar.setText("0.00");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAniadirPasajero;
    private javax.swing.JButton btnComprar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxFecha;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPaneAsientos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton rbtnAñadir;
    private javax.swing.JRadioButton rbtnNoAñadir;
    private javax.swing.JTable tableVuelos;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JLabel txtCedulaTarjeta;
    private javax.swing.JLabel txtCupos;
    private javax.swing.JTextField txtFechaNacimiento;
    private javax.swing.JLabel txtNombreUsuarioTarjeta;
    private javax.swing.JTextField txtNombresApellidos;
    private javax.swing.JLabel txtNumeroPasajeros;
    private javax.swing.JLabel txtPrecioBase;
    private javax.swing.JLabel txtTotalPagar;
    private javax.swing.JLabel txtusuario;
    // End of variables declaration//GEN-END:variables
}
