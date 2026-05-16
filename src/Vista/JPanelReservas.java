/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.TripulacionController;
import Modelo.Tripulacion;
import controlador.LoginController;
import controlador.VueloController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Vuelo;

/**
 *
 * @author admin
 */
public class JPanelReservas extends javax.swing.JPanel {

    private VueloController vueloController;
    Cliente cliente = LoginController.getClienteActual();
    private boolean cargandoCombos = true;

    /**
     * Creates new form JPanelAeronave
     */
    public JPanelReservas() {
        initComponents();
        vueloController = new VueloController();
        txtusuario.setText(" " + cliente.getNombres() + cliente.getApellidos());
        cargarTarjeta();
        cargarCombos();
        cargarTablaVuelos(vueloController.listarVuelos());
        eventosCombos();
    }

    private void cargarTarjeta() {
        txtNombreUsuarioTarjeta.setText(" " + cliente.getNombres() + " " + cliente.getApellidos());
        txtCedulaTarjeta.setText(cliente.getCedula());
    }

//    private void cargarCombos() {
//
//        cbxOrigen.removeAllItems();
//        cbxDestino.removeAllItems();
//        cbxFecha.removeAllItems();
//
//        cbxOrigen.addItem("Seleccione");
//        cbxDestino.addItem("Seleccione");
//        cbxFecha.addItem("Seleccione");
//
//        for (String origen : vueloController.obtenerOrigenes()) {
//            cbxOrigen.addItem(origen);
//        }
//
//        for (String destino : vueloController.obtenerDestinos()) {
//            cbxDestino.addItem(destino);
//        }
//
//        for (String fecha : vueloController.obtenerFechas()) {
//            cbxFecha.addItem(fecha);
//        }
//    }
//    private void cargarCombos() {
//
//        cbxOrigen.removeAllItems();
//        cbxDestino.removeAllItems();
//        cbxFecha.removeAllItems();
//
//        cbxOrigen.addItem("Seleccione");
//        cbxDestino.addItem("Seleccione");
//        cbxFecha.addItem("Seleccione");
//
//        for (String origen : vueloController.obtenerOrigenes()) {
//            cbxOrigen.addItem(origen);
//        }
//
//        for (String destino : vueloController.obtenerDestinos()) {
//            cbxDestino.addItem(destino);
//        }
//
//        for (String fecha : vueloController.obtenerFechas()) {
//            cbxFecha.addItem(fecha);
//        }
//    }
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

        for (Vuelo v : lista) {

            modelo.addRow(new Object[]{
                v.getCodigo(),
                v.getOrigen(),
                v.getDestino(),
                v.getFechaSalida(),
                v.getPrecioBase()
            });
        }

        tableVuelos.setModel(modelo);
    }

//    private void filtrarVuelos() {
//
//        String origen = cbxOrigen.getSelectedItem().toString();
//        String destino = cbxDestino.getSelectedItem().toString();
//        String fecha = cbxFecha.getSelectedItem().toString();
//
//        // Si aún no selecciona todos los filtros
//        if (origen.equals("Seleccione")
//                || destino.equals("Seleccione")
//                || fecha.equals("Seleccione")) {
//
//            cargarTablaVuelos(vueloController.listarVuelos());
//            return;
//        }
//
//        // Obtener vuelos filtrados
//        List<Vuelo> listaFiltrada
//                = vueloController.filtrarVuelos(origen, destino, fecha);
//
//        // Validar si no hay resultados
//        if (listaFiltrada.isEmpty()) {
//
//            DefaultTableModel modelo
//                    = (DefaultTableModel) tableVuelos.getModel();
//
//            modelo.setRowCount(0);
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "No hay vuelos disponibles con esos filtros"
//            );
//
//        } else {
//
//            cargarTablaVuelos(listaFiltrada);
//        }
//    }
//    private void filtrarVuelos() {
//
//        String origen = cbxOrigen.getSelectedItem().toString();
//        String destino = cbxDestino.getSelectedItem().toString();
//        String fecha = cbxFecha.getSelectedItem().toString();
//
//        if (origen.equals("Seleccione")
//                || destino.equals("Seleccione")
//                || fecha.equals("Seleccione")) {
//
//            cargarTablaVuelos(vueloController.listarVuelos());
//            return;
//        }
//
//        List<Vuelo> listaFiltrada
//                = vueloController.filtrarVuelos(origen, destino, fecha);
//
//        if (listaFiltrada.isEmpty()) {
//
//            ((DefaultTableModel) tableVuelos.getModel()).setRowCount(0);
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "No hay vuelos disponibles con esos filtros"
//            );
//
//        } else {
//            cargarTablaVuelos(listaFiltrada);
//        }
//    }

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
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNombresApellidos = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFechaNacimiento = new javax.swing.JTextField();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
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
        btnAñadirPasajero = new javax.swing.JButton();
        cbxFecha = new javax.swing.JComboBox<>();
        cbxDestino = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cbxOrigen = new javax.swing.JComboBox<>();

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

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel13.setText("x");
        jLabel13.setToolTipText("");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 70, 20));

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
        jLabel10.setText("Fecha de Nacimiento");
        jLabel10.setToolTipText("");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 160, -1));

        txtFechaNacimiento.setEditable(false);
        txtFechaNacimiento.setEnabled(false);
        jPanel3.add(txtFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 230, 30));

        jRadioButton2.setText("Añadir (+)");
        jPanel3.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        jRadioButton3.setText("No añadir (-)");
        jPanel3.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, -1, -1));

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel15.setText("3. Registro de Pasajeros");
        jLabel15.setToolTipText("");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, 20));

        javax.swing.GroupLayout jPaneAsientosLayout = new javax.swing.GroupLayout(jPaneAsientos);
        jPaneAsientos.setLayout(jPaneAsientosLayout);
        jPaneAsientosLayout.setHorizontalGroup(
            jPaneAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );
        jPaneAsientosLayout.setVerticalGroup(
            jPaneAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        jPanel3.add(jPaneAsientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 210, 390));

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreUsuarioTarjeta)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCedulaTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addComponent(btnComprar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 230, 130));

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel18.setText("Cédula");
        jLabel18.setToolTipText("");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 160, -1));

        btnAñadirPasajero.setBackground(new java.awt.Color(204, 204, 204));
        btnAñadirPasajero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAñadirPasajero.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadirPasajero.setText("Añadir Pasajero ");
        btnAñadirPasajero.setBorder(null);
        btnAñadirPasajero.setEnabled(false);
        btnAñadirPasajero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirPasajeroActionPerformed(evt);
            }
        });
        jPanel3.add(btnAñadirPasajero, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 160, 30));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(46, 46, 46))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 42, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(445, 445, 445)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprarActionPerformed

    }//GEN-LAST:event_btnComprarActionPerformed

    private void btnAñadirPasajeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirPasajeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAñadirPasajeroActionPerformed

    private void setear() {
//        txtNombresApellidos.setText("");
//        txtApellidos.setText("");
//        txtCedula.setText("");
//        txtLicencia.setText("");
//        cbxRol.setSelectedIndex(0);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadirPasajero;
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
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tableVuelos;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JLabel txtCedulaTarjeta;
    private javax.swing.JTextField txtFechaNacimiento;
    private javax.swing.JLabel txtNombreUsuarioTarjeta;
    private javax.swing.JTextField txtNombresApellidos;
    private javax.swing.JLabel txtNumeroPasajeros;
    private javax.swing.JLabel txtPrecioBase;
    private javax.swing.JLabel txtTotalPagar;
    private javax.swing.JLabel txtusuario;
    // End of variables declaration//GEN-END:variables
}
