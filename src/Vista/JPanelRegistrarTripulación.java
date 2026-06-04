/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.TripulacionController;
import Modelo.Tripulacion;
import controlador.LoginController;
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
import modelo.Cliente;

/**
 *
 * @author admin
 */
public class JPanelRegistrarTripulación extends javax.swing.JPanel {

    private TripulacionController tripuController;

    /**
     * Creates new form JPanelAeronave
     */
    public JPanelRegistrarTripulación() {
        initComponents();
        tripuController = new TripulacionController();
        Cliente cliente = LoginController.getClienteActual();
        txtusuario.setText(" " + cliente.getNombres());

        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txtBuscador.getText().trim();
                if (texto.isEmpty()) {
                    cargarTarjetasTripulacion();
                }
            }
        });

        this.cargarTarjetasTripulacion();
        JpanelCards.setLayout(new BoxLayout(JpanelCards, BoxLayout.Y_AXIS));

        cargarTarjetasTripulacion();

    }

    private void cargarTarjetasTripulacion() {

        JpanelCards.removeAll();

        JpanelCards.setLayout( new BoxLayout(JpanelCards,BoxLayout.Y_AXIS));

        List<Tripulacion> lista = tripuController.listarTripulacion();

        for (Tripulacion t : lista) {
            JPanel tarjeta = crearTarjeta(t);
            
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
            wrapper.setOpaque(false);
            wrapper.setPreferredSize( new Dimension(348, 110));
            wrapper.setMaximumSize( new Dimension(348, 110));
            wrapper.setMinimumSize(new Dimension(348, 110) );
            wrapper.add(tarjeta);

            JpanelCards.add(wrapper);
        }

        JpanelCards.add(Box.createVerticalGlue());
        JpanelCards.revalidate();
        JpanelCards.repaint();
    }

    private JPanel crearTarjeta(Tripulacion t) {
        JPanel card = new JPanel();
        
        card.setPreferredSize( new Dimension(338, 110));
        card.setMaximumSize(new Dimension(338, 110));
        card.setMinimumSize(new Dimension(338, 110));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.setLayout(null);

        JLabel foto = new JLabel();
        foto.setBounds(10, 20, 50, 50);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/profile.png"));
        Image img = icon.getImage().getScaledInstance(45,45,Image.SCALE_SMOOTH);
        foto.setIcon(new ImageIcon(img));

        JLabel lblNombre = new JLabel(t.getNombre() + " " + t.getApellido() );
        lblNombre.setBounds(65,8,170,20);
        lblNombre.setFont(new Font("Segoe UI",Font.BOLD,14));

        JLabel txtId = new JLabel("ID Personal:");
        txtId.setBounds(65,35,70,18);
        txtId.setFont(new Font("Segoe UI",Font.BOLD,11));

        JLabel lblId = new JLabel("CA-00" + t.getIdTripulante());
        lblId.setBounds(138,35,70,18);
        lblId.setFont(new Font("Segoe UI",Font.PLAIN,11));
        
        JLabel txtRol = new JLabel("Rol:");
        txtRol.setBounds(65,55,35,18);
        txtRol.setFont(new Font("Segoe UI",Font.BOLD,11 ));

        JLabel lblRol = new JLabel(t.getRol());
        lblRol.setBounds(105,55,85,18);
        lblRol.setOpaque(true);
        lblRol.setHorizontalAlignment(SwingConstants.CENTER );
        lblRol.setFont(new Font("Segoe UI",Font.PLAIN,10));

        if (t.getRol().equalsIgnoreCase("Piloto")) {
            lblRol.setBackground(new Color(198, 239, 206));
            lblRol.setForeground(new Color(0, 97, 0));
        } else if (t.getRol().equalsIgnoreCase("Copiloto")) {
            lblRol.setBackground(new Color(221, 235, 247) );
            lblRol.setForeground(new Color(0, 76, 153) );
        } else {
            lblRol.setBackground(new Color(242, 242, 242));
            lblRol.setForeground(Color.DARK_GRAY);
        }

        JLabel txtLicencia = new JLabel("Licencia:");
        txtLicencia.setBounds(65,75,60,18);
        txtLicencia.setFont(new Font("Segoe UI",Font.BOLD,11));

        JLabel lblLicencia = new JLabel(t.getLicencia());
        lblLicencia.setBounds(128,75,100,18);
        lblLicencia.setFont(new Font("Segoe UI",Font.PLAIN,11));
        
        card.add(foto);
        card.add(lblNombre);
        
        card.add(txtId);
        card.add(lblId);

        card.add(txtRol);
        card.add(lblRol);

        card.add(txtLicencia);
        card.add(lblLicencia);

        return card;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtusuario = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        txtBuscador = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JpanelCards = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtLicencia = new javax.swing.JTextField();
        btnCerrarSesion = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        txtCedula = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(225, 238, 250));

        txtusuario.setBackground(new java.awt.Color(255, 255, 255));
        txtusuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/profile.png"))); // NOI18N
        txtusuario.setText("Usuario");

        jSeparator2.setBackground(new java.awt.Color(0, 51, 102));
        jSeparator2.setForeground(new java.awt.Color(0, 51, 102));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyPressed(evt);
            }
        });
        jPanel3.add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 160, 20));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel3.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 40, 20));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel10.setText("Listado de Personal");
        jLabel10.setToolTipText("");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, 20));

        jScrollPane1.setViewportView(JpanelCards);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 350, 330));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel8.setText("Cédula");
        jLabel8.setToolTipText("");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 160, -1));

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel11.setText("Registrar Tripulante");
        jLabel11.setToolTipText("");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 160, -1));

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel12.setText("Nombres");
        jLabel12.setToolTipText("");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 160, -1));

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel13.setText("Apellidos");
        jLabel13.setToolTipText("");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 160, -1));
        jPanel4.add(txtLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 250, 30));

        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(0, 102, 204));
        btnCerrarSesion.setText("Cancelar");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        jPanel4.add(btnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 120, 30));

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
        jPanel4.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 120, 30));

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel14.setText("Licencia");
        jLabel14.setToolTipText("");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 160, -1));
        jPanel4.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 250, 30));

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel15.setText("Rol");
        jLabel15.setToolTipText("");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 160, -1));

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Piloto", "Copiloto", "Asistente" }));
        cbxRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxRolActionPerformed(evt);
            }
        });
        jPanel4.add(cbxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 250, 30));
        jPanel4.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 250, 30));
        jPanel4.add(txtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 250, 30));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel9.setText("GESTIÓN DE TRIPULACIÓN");
        jLabel9.setToolTipText("");

        jButton1.setBackground(new java.awt.Color(225, 238, 250));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"))); // NOI18N
        jButton1.setBorder(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(582, Short.MAX_VALUE)
                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(40, 40, 40))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(446, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGap(15, 15, 15)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(17, 17, 17)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        this.setear();
        this.cargarTarjetasTripulacion();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void setear() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCedula.setText("");
        txtLicencia.setText("");
        cbxRol.setSelectedIndex(0);
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String cedula = txtCedula.getText().trim();
        String licencia = txtLicencia.getText().trim();
        String rol = cbxRol.getSelectedItem().toString();
        Tripulacion trip = new Tripulacion();
        trip.setNombre(nombres);
        trip.setApellido(apellidos);
        trip.setCedula(cedula);
        trip.setLicencia(licencia);
        trip.setRol(rol);
        boolean registrado = tripuController.crearTripu(trip);

        if (registrado) {
            JOptionPane.showMessageDialog(
                    this,
                    tripuController.getMensaje(),
                    "Éxito",
            JOptionPane.INFORMATION_MESSAGE
            );
            setear();
            cargarTarjetasTripulacion();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    tripuController.getMensaje(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.buscarTripulacion();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            this.buscarTripulacion();
        }
    }//GEN-LAST:event_txtBuscadorKeyPressed

    private void cbxRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxRolActionPerformed

    private void buscarTripulacion() {
        String criterio = txtBuscador.getText().trim();
        
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Ingrese un criterio de búsqueda" );
            cargarTarjetasTripulacion();

            return;
        }

        List<Tripulacion> tripEncontrados = tripuController.buscarTripulacion(criterio);
        JpanelCards.removeAll();
        
        if (!tripEncontrados.isEmpty()) {
            for (Tripulacion t : tripEncontrados) {
                JPanel tarjeta = crearTarjeta(t);

                JPanel wrapper = new JPanel(new FlowLayout(  FlowLayout.LEFT,5,5));
                wrapper.setOpaque(false);
                wrapper.setPreferredSize(new Dimension(348, 110));
                wrapper.setMaximumSize(new Dimension(348, 110));
                wrapper.setMinimumSize(new Dimension(348, 110));
                wrapper.add(tarjeta);

                JpanelCards.add(wrapper);
            }
        } else {
            JOptionPane.showMessageDialog(null,"No se encontraron resultados" );
            cargarTarjetasTripulacion();
        }

        JpanelCards.revalidate();
        JpanelCards.repaint();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JpanelCards;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscador;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtLicencia;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JLabel txtusuario;
    // End of variables declaration//GEN-END:variables
}
