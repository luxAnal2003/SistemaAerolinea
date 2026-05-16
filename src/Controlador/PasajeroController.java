/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Aeronave;
import Modelo.PasajeroExtra;
import utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class PasajeroController {
//
//    private Connection con;
//
//    public PasajeroController() {
//        con = DatabaseConnection.getConnection();
//    }
//    
//    public void guardarPasajero(PasajeroExtra p, int idCliente, int idVuelo) throws SQLException {
//
//        String sql = "INSERT INTO pasajeros_extra(id_cliente, id_vuelo, nombre, identificacion, fecha_nacimiento, asiento) VALUES (?, ?, ?, ?, ?, ?)";
//
//        PreparedStatement ps = con.prepareStatement(sql);
//
//        ps.setInt(1, idCliente);
//        ps.setInt(2, idVuelo);
//        ps.setString(3, p.getNombre());
//        ps.setString(4, p.getIdentificacion());
//
//        // FIX del error Date.valueOf
//        ps.setDate(5, java.sql.Date.valueOf(p.getFechaNacimiento()));
//
//        ps.setString(6, p.getAsiento());
//
//        ps.executeUpdate();
//    }
//    
//    private boolean validarDatosPasajero(PasajeroExtra p) {
//        if (p == null) {
//            return false;
//        }
//        if (p.getModelo() == null|| p.getModelo().trim().isEmpty()) {
//            System.err.println("El modelo es obligatorio");
//            return false;
//        }
//        if (p.getCapacidad() <= 0) {
//            System.err.println("La capacidad debe ser mayor a 0" );
//            return false;
//        }
//        if (p.getEstado() == null || p.getEstado().trim().isEmpty()) {
//            System.err.println("El estado es obligatorio"  );
//            return false;
//        }
//        return true;
//    }
}
