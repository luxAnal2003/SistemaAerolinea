/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import controlador.ReservaController;
import Modelo.PasajeroExtra;
import Modelo.Reserva;
import controlador.LoginController;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

/**
 *
 * @author admin
 */
public class JPanelEditarReservas extends javax.swing.JPanel {

    Cliente cliente = LoginController.getClienteActual();

    private List<PasajeroExtra> pasajeros = new ArrayList<>();
    private ReservaController reservaController = new ReservaController();
    private JPanelAsientos panelAsientos;
    private DefaultTableModel modeloTabla;
    private String pasajeroSeleccionado;
    private String asientoActual;
    private boolean esTitular = false;
    private int idReservaSeleccionada;

    /**
     * Creates new form JPanelAeronave
     */
    public JPanelEditarReservas() {
        initComponents();
        txtusuario.setText(" " + cliente.getNombres() + " " + cliente.getApellidos());

        panelAsientos = new JPanelAsientos();
        jPaneAsientos.setLayout(new BorderLayout());
        jPaneAsientos.add(panelAsientos, BorderLayout.CENTER);

        tableReservas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tableReservas.getSelectedRow();
                if (fila != -1) {
                    int idReserva = Integer.parseInt(tableReservas.getValueAt(fila, 0).toString());
                    if (reservaController.reservaCancelada(idReserva)) {
                        JOptionPane.showMessageDialog(this,"La reserva está cancelada y no puede modificarse.");
                        tablePasajeros.setModel(new DefaultTableModel());
                        panelAsientos.limpiarSeleccion();
                        pasajeroSeleccionado = null;
                        asientoActual = null;
                        return;
                    }
                    idReservaSeleccionada = idReserva;
                    if (reservaController.reservaCancelada(idReserva)) {
                        JOptionPane.showMessageDialog(this,"La reserva está cancelada y no puede modificarse.");

                        DefaultTableModel modelo = new DefaultTableModel();
                        modelo.setColumnIdentifiers(new Object[]{"Pasajero", "Asiento"});
                        tablePasajeros.setModel(modelo);

                        panelAsientos.limpiarSeleccion();
                        btnActualizar.setEnabled(false);
                        return;
                    }
                    btnActualizar.setEnabled(true);
                    panelAsientos.limpiarSeleccion();
                    cargarPasajeros(idReserva);
                }
            }
        });
        tablePasajeros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablePasajeros.getSelectedRow();
                if (fila != -1) {
                    PasajeroExtra p = pasajeros.get(fila);
                    pasajeroSeleccionado = p.getIdentificacion();
                    asientoActual = p.getAsiento();
                    esTitular = p.getTipo().equals("TITULAR");
                    panelAsientos.seleccionarAsiento(asientoActual);
                }
            }
        });
        cargarReservas();
        txtBuscador.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void actualizar() {
                if (txtBuscador.getText().trim().isEmpty()) {
                    cargarReservas();
                }
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                actualizar();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                actualizar();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                actualizar();
            }
        });
    }

    private void cargarPasajeros(int idReserva) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(
                new Object[]{
                    "Pasajero",
                    "Asiento"
                }
        );
        pasajeros = reservaController.obtenerPasajerosReserva(idReserva);

        for (PasajeroExtra p : pasajeros) {
            modelo.addRow(
                    new Object[]{
                        p.getNombre(),
                        p.getAsiento()
                    }
            );
        }
        tablePasajeros.setModel(modelo);
    }

    private void mostrarReservas(List<Reserva> lista) {
        modeloTabla.setRowCount(0);
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

    private void cargarReservas() {
        if (modeloTabla == null) {
            modeloTabla = new DefaultTableModel();
            modeloTabla.setColumnIdentifiers(new Object[]{
                "Reserva",
                "Ruta",
                "Fecha",
                "Pasajeros",
                "Total",
                "Estado",
                "Asiento"
            });
            tableReservas.setModel(modeloTabla);
        }
        mostrarReservas(reservaController.listarReservas(cliente.getId()));
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
        jLabel11 = new javax.swing.JLabel();
        jPaneAsientos = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        txtBuscador = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePasajeros = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableReservas = new javax.swing.JTable();

        setBackground(new java.awt.Color(225, 238, 250));

        txtusuario.setBackground(new java.awt.Color(255, 255, 255));
        txtusuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile.png"))); // NOI18N
        txtusuario.setText("Usuario");

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel9.setText("EDITAR RESERVAS - CAMBIO DE ASIENTO");
        jLabel9.setToolTipText("");

        jButton1.setBackground(new java.awt.Color(225, 238, 250));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"))); // NOI18N
        jButton1.setBorder(null);

        jSeparator2.setBackground(new java.awt.Color(0, 51, 102));
        jSeparator2.setForeground(new java.awt.Color(0, 51, 102));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel11.setText("Pasajeros y asiento:");
        jLabel11.setToolTipText("");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 150, 20));

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

        jPanel3.add(jPaneAsientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 320, 390));

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel19.setText("Seleccionar asientos");
        jLabel19.setToolTipText("");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, 20));

        btnActualizar.setBackground(new java.awt.Color(0, 102, 204));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(null);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel3.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 130, 20));

        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyPressed(evt);
            }
        });
        jPanel3.add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 360, 20));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel3.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 40, 20));

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel13.setText("BUSCAR: ");
        jLabel13.setToolTipText("");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, 20));

        tablePasajeros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Reserva", "Pasajero", "Asiento"
            }
        ));
        jScrollPane3.setViewportView(tablePasajeros);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 480, 140));

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel12.setText("Seleccione una reserva");
        jLabel12.setToolTipText("");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 20));

        tableReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Reserva", "Ruta", "Fecha", "Pasajeros", "Total", "Estado", "Asiento"
            }
        ));
        jScrollPane4.setViewportView(tableReservas);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 480, 170));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed

        if (reservaController.reservaCancelada(idReservaSeleccionada)) {
            JOptionPane.showMessageDialog(this,"No es posible modificar una reserva cancelada.");
            return;
        }

        if (pasajeroSeleccionado == null) {
            JOptionPane.showMessageDialog(this,"Seleccione un pasajero" );
            return;
        }

        List<String> seleccionados = panelAsientos.getAsientosSeleccionados();

        if (seleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Seleccione un asiento");
            return;
        }

        if (seleccionados.size() > 1) {
            JOptionPane.showMessageDialog(this,"Solo puede modificar el asiento de un pasajero a la vez.");
            return;
        }

        String nuevoAsiento = seleccionados.get(0);

        String respuesta;

        if (esTitular) {
            respuesta= reservaController.cambiarAsientoTitular(idReservaSeleccionada, nuevoAsiento);
        } else {
            respuesta= reservaController.cambiarAsientoPasajero(pasajeroSeleccionado, nuevoAsiento);
        }

        JOptionPane.showMessageDialog(this,respuesta);
        if (respuesta.equals(
                "Asiento actualizado correctamente")) {
            cargarReservas();
            cargarPasajeros(idReservaSeleccionada);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void buscarReserva() {
        String criterio = txtBuscador.getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Ingrese un criterio de búsqueda");
            return;
        }

        List<Reserva> lista = reservaController.buscarReservas(cliente.getId(), criterio);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,"No se encontraron resultados");
            return;
        }
        mostrarReservas(lista);
    }

    private void txtBuscadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            buscarReserva();
        }
    }//GEN-LAST:event_txtBuscadorKeyPressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarReserva();
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPaneAsientos;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tablePasajeros;
    private javax.swing.JTable tableReservas;
    private javax.swing.JTextField txtBuscador;
    private javax.swing.JLabel txtusuario;
    // End of variables declaration//GEN-END:variables
}
