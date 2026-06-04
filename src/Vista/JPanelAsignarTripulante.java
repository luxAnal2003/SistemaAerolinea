/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.CDUVAeronaveController;
import Controlador.TripulacionController;
import Controlador.VueloTripulacionController;
import Modelo.Aeronave;
import Modelo.Tripulacion;
import controlador.LoginController;
import controlador.VueloController;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Vuelo;

/**
 *
 * @author admin
 */
public class JPanelAsignarTripulante extends javax.swing.JPanel {

    private CDUVAeronaveController aeroController;
    private TripulacionController tripController;
    private VueloController vueloController;
    private VueloTripulacionController asignacionController;
    private List<Vuelo> listaVuelos;

    private List<Aeronave> listaAeronaves;
    private List<Tripulacion> listaPilotos;
    private List<Tripulacion> listaCopilotos;
    private List<Tripulacion> listaAsistentes;

    /**
     * Creates new form JPanelAeronave
     */
    public JPanelAsignarTripulante() {
        initComponents();
        aeroController = new CDUVAeronaveController();
        tripController = new TripulacionController();
        vueloController = new VueloController();
        asignacionController = new VueloTripulacionController();

        listaAeronaves = aeroController.listarAeronaves();
        Cliente cliente = LoginController.getClienteActual();
        txtusuario.setText(" " + cliente.getNombres());

        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String Texto = txtBuscador.getText().trim();
                if (Texto.isEmpty()) {
                    cargarVuelosEnTabla();
                }
            }
        });
        this.cargarTripulacion();
        cargarVuelosEnTabla();
        listAsistentes.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        if (listaAsistentes.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay asistentes disponibles"
            );

            return;
        }
        tableVuelos.getSelectionModel().addListSelectionListener(e -> {

            int fila = tableVuelos.getSelectedRow();

            if (fila >= 0) {

                seleccionarVuelo(fila);
            }
        });
        setear();
    }

    private void seleccionarVuelo(int fila) {

        if (listaAeronaves == null || listaAeronaves.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen aeronaves registradas"
            );

            return;
        }

        Vuelo vuelo = listaVuelos.get(fila);

        int idAeronave = vuelo.getIdAeronave();

        Aeronave aeronaveSeleccionada = null;

        for (Aeronave a : listaAeronaves) {

            if (a.getIdAeronave() == idAeronave) {

                aeronaveSeleccionada = a;
                break;
            }
        }

        if (aeronaveSeleccionada != null) {

            txtAeronaveActiva.setText(
                    aeronaveSeleccionada.getModelo()
                    + " | "
                    + aeronaveSeleccionada.getEstado()
            );

            mostrarInfoAeronave(aeronaveSeleccionada);
        }
    }

    private void mostrarInfoAeronave(Aeronave a) {

        lblModelo.setText(a.getModelo());

        lblRegistro.setText("A-" + a.getIdAeronave());

        lblCapacidad.setText(
                String.valueOf(a.getCapacidad())
        );

        lblEstado.setText(a.getEstado());

        if (a.getEstado().equalsIgnoreCase("Mantenimiento")) {

            lblMantenimiento.setText("Sí");

        } else {

            lblMantenimiento.setText("No");
        }
    }

    private void cargarTripulacion() {

        List<Tripulacion> lista
                = tripController.listarTripulacion();

        listaPilotos = new ArrayList<>();
        listaCopilotos = new ArrayList<>();
        listaAsistentes = new ArrayList<>();

        cbxPiloto.removeAllItems();
        cbxCopiloto.removeAllItems();

        cbxPiloto.addItem("Seleccione...");
        cbxCopiloto.addItem("Seleccione...");

        DefaultListModel<String> modeloLista
                = new DefaultListModel<>();

        for (Tripulacion t : lista) {

            String nombreCompleto
                    = t.getNombre()
                    + " "
                    + t.getApellido();

            if (t.getRol().equalsIgnoreCase("Piloto")) {

                listaPilotos.add(t);

                cbxPiloto.addItem(nombreCompleto);
            } else if (t.getRol().equalsIgnoreCase("Copiloto")) {

                listaCopilotos.add(t);

                cbxCopiloto.addItem(nombreCompleto);
            } else if (t.getRol().equalsIgnoreCase("Asistente")) {

                listaAsistentes.add(t);

                modeloLista.addElement(nombreCompleto);
            }
        }

        listAsistentes.setModel(modeloLista);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        txtBuscador = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        lblRegistro = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblCapacidad = new javax.swing.JLabel();
        lblMantenimiento = new javax.swing.JLabel();
        txtAeronaveActiva = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cbxPiloto = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cbxCopiloto = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listAsistentes = new javax.swing.JList<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txtusuario = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableVuelos = new javax.swing.JTable();

        setBackground(new java.awt.Color(225, 238, 250));

        jSeparator2.setBackground(new java.awt.Color(0, 51, 102));
        jSeparator2.setForeground(new java.awt.Color(0, 51, 102));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyPressed(evt);
            }
        });
        jPanel3.add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 20));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel3.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 40, 20));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel10.setText("1. Seleccionar Vuelos");
        jLabel10.setToolTipText("");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 20));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel9.setText("ASIGNACIÓN OPERATIVA DE VUELOS");
        jLabel9.setToolTipText("");

        jButton1.setBackground(new java.awt.Color(225, 238, 250));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"))); // NOI18N
        jButton1.setBorder(null);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 10)); // NOI18N
        jLabel14.setText("AERONAVE ACTIVA");
        jLabel14.setToolTipText("");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 150, 20));

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel16.setText("2. Aeronave Relacionada");
        jLabel16.setToolTipText("");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, 20));

        jPanel1.setBackground(new java.awt.Color(225, 238, 250));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Modelo: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Registro:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Capacidad:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Estado:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Mantenimiento: ");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/one-way-trip.png"))); // NOI18N

        lblModelo.setText("Modelo: ");

        lblRegistro.setText("Modelo: ");

        lblEstado.setText("Modelo: ");

        lblCapacidad.setText("Modelo: ");

        lblMantenimiento.setText("Modelo: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblModelo))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRegistro))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCapacidad))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMantenimiento))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEstado)))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblModelo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblRegistro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblCapacidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblMantenimiento))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 180, 280));

        txtAeronaveActiva.setEditable(false);
        txtAeronaveActiva.setBackground(new java.awt.Color(255, 255, 255));
        txtAeronaveActiva.setEnabled(false);
        jPanel5.add(txtAeronaveActiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 180, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel15.setText("3. Asignar Tripulación");
        jLabel15.setToolTipText("");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, 20));

        cbxPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", " " }));
        cbxPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPilotoActionPerformed(evt);
            }
        });
        jPanel6.add(cbxPiloto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 190, -1));

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 1, 10)); // NOI18N
        jLabel17.setText("PILOTO:");
        jLabel17.setToolTipText("");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, 20));

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 1, 10)); // NOI18N
        jLabel18.setText("ASISTENTES:");
        jLabel18.setToolTipText("");
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 150, 20));

        cbxCopiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", " " }));
        cbxCopiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCopilotoActionPerformed(evt);
            }
        });
        jPanel6.add(cbxCopiloto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 190, -1));

        listAsistentes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listAsistentes);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 190, 180));

        btnGuardar.setBackground(new java.awt.Color(0, 102, 204));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel6.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 120, 30));

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 102, 204));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel6.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 120, 30));

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 1, 10)); // NOI18N
        jLabel20.setText("COPILOTO:");
        jLabel20.setToolTipText("");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 150, 20));

        txtusuario.setBackground(new java.awt.Color(255, 255, 255));
        txtusuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile.png"))); // NOI18N
        txtusuario.setText("Usuario");

        tableVuelos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Origen", "Destino", "Horario"
            }
        ));
        jScrollPane2.setViewportView(tableVuelos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(40, 40, 40))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGap(15, 15, 15)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(17, 17, 17)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setear() {
        txtBuscador.setText("");
        cbxPiloto.setSelectedIndex(0);
        cbxCopiloto.setSelectedIndex(0);
        txtAeronaveActiva.setText("");
        limpiarLabelsAeronave();
    }

    private void limpiarLabelsAeronave() {
        lblModelo.setText("");
        lblRegistro.setText("");
        lblCapacidad.setText("");
        lblEstado.setText("");
        lblMantenimiento.setText("");
    }
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarVuelo();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            buscarVuelo();
        }
    }//GEN-LAST:event_txtBuscadorKeyPressed

    private void cbxPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPilotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxPilotoActionPerformed

    private void cbxCopilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCopilotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCopilotoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int fila = tableVuelos.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un vuelo"
            );

            return;
        }

        Vuelo vuelo = listaVuelos.get(fila);

        Aeronave aeronave = null;

        int idAeronave = vuelo.getIdAeronave();

        for (Aeronave a : listaAeronaves) {

            if (a.getIdAeronave() == idAeronave) {

                aeronave = a;
                break;
            }
        }

        Tripulacion piloto = null;

        if (cbxPiloto.getSelectedIndex() > 0) {

            piloto = listaPilotos.get(
                    cbxPiloto.getSelectedIndex() - 1
            );
        }

        Tripulacion copiloto = null;

        if (cbxCopiloto.getSelectedIndex() > 0) {

            copiloto = listaCopilotos.get(
                    cbxCopiloto.getSelectedIndex() - 1
            );
        }

        List<Tripulacion> asistentesSeleccionados
                = new ArrayList<>();

        for (int index : listAsistentes.getSelectedIndices()) {

            asistentesSeleccionados.add(
                    listaAsistentes.get(index)
            );
        }

        String validacion
                = asignacionController.validarAsignacion(
                        aeronave,
                        piloto,
                        copiloto,
                        asistentesSeleccionados
                );

        if (validacion != null) {

            JOptionPane.showMessageDialog(
                    this,
                    validacion
            );

            return;
        }
        boolean guardado
                = asignacionController.guardarAsignacion(
                        vuelo,
                        piloto,
                        copiloto,
                        asistentesSeleccionados
                );

        if (guardado) {

            String resumen
                    = asignacionController.generarResumen(
                            vuelo,
                            aeronave,
                            piloto,
                            copiloto,
                            asistentesSeleccionados
                    );

            JOptionPane.showMessageDialog(
                    this,
                    resumen
                    + "\n\nAsignación guardada correctamente."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar asignación"
            );
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setear();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void buscarVuelo() {

        String criterio = txtBuscador.getText().trim();

        if (criterio.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese un criterio de búsqueda"
            );

            cargarVuelosEnTabla();
            return;
        }

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(
                new Object[]{
                    "ID",
                    "Origen",
                    "Destino",
                    "Horario",}
        );

        List<Vuelo> vuelosEncontrados
                = vueloController.buscarVuelo(criterio);

        if (!vuelosEncontrados.isEmpty()) {

            for (Vuelo v : vuelosEncontrados) {

                Object[] fila = new Object[8];

                fila[0] = v.getCodigo();
                fila[1] = v.getOrigen();
                fila[2] = v.getDestino();
                fila[3] = v.getFechaSalida();

                model.addRow(fila);
            }

            tableVuelos.setModel(model);
            jScrollPane2.setViewportView(tableVuelos);

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "No se encontraron resultados"
            );

            cargarVuelosEnTabla();
        }
    }

    private void cargarVuelosEnTabla() {

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(
                new Object[]{
                    "Código",
                    "Origen",
                    "Destino",
                    "Fecha",
                    "Aeronave ID"
                }
        );

        listaVuelos = vueloController.listarVuelos();

        for (Vuelo v : listaVuelos) {

            model.addRow(new Object[]{
                v.getCodigo(),
                v.getOrigen(),
                v.getDestino(),
                v.getFechaSalida(),
                v.getIdAeronave()
            });
        }

        tableVuelos.setModel(model);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxCopiloto;
    private javax.swing.JComboBox<String> cbxPiloto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblMantenimiento;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblRegistro;
    private javax.swing.JList<String> listAsistentes;
    private javax.swing.JTable tableVuelos;
    private javax.swing.JTextField txtAeronaveActiva;
    private javax.swing.JTextField txtBuscador;
    private javax.swing.JLabel txtusuario;
    // End of variables declaration//GEN-END:variables
}
