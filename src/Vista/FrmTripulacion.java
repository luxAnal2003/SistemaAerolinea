/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class FrmTripulacion extends JFrame{
    
    public JTextField txtNombre;
    public JTextField txtLicencia;

    public JComboBox<String> cbRol;

    public JButton btnGuardar;
    public JButton btnEditar;
    public JButton btnEliminar;

    public JTable tablaTripulacion;

    public FrmTripulacion() {

        setTitle("Gestión de Tripulación");

        setSize(700,500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        JLabel lblNombre =
                new JLabel("Nombre:");

        lblNombre.setBounds(30,30,100,25);

        add(lblNombre);

        txtNombre = new JTextField();

        txtNombre.setBounds(130,30,220,25);

        add(txtNombre);

        JLabel lblRol =
                new JLabel("Rol:");

        lblRol.setBounds(30,70,100,25);

        add(lblRol);

        cbRol = new JComboBox<>();

        cbRol.addItem("Piloto");
        cbRol.addItem("Asistente");

        cbRol.setBounds(130,70,220,25);

        add(cbRol);

        JLabel lblLicencia =
                new JLabel("Licencia:");

        lblLicencia.setBounds(30,110,100,25);

        add(lblLicencia);

        txtLicencia = new JTextField();

        txtLicencia.setBounds(130,110,220,25);

        add(txtLicencia);

        btnGuardar =
                new JButton("Guardar");

        btnGuardar.setBounds(30,170,100,30);

        add(btnGuardar);

        btnEditar =
                new JButton("Editar");

        btnEditar.setBounds(140,170,100,30);

        add(btnEditar);

        btnEliminar =
                new JButton("Eliminar");

        btnEliminar.setBounds(250,170,100,30);

        add(btnEliminar);

        tablaTripulacion = new JTable();

        JScrollPane sp =
                new JScrollPane(tablaTripulacion);

        sp.setBounds(30,230,620,180);

        add(sp);

        setLocationRelativeTo(null);
    }
}
