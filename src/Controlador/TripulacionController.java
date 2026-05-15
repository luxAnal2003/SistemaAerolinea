/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Tripulacion;
import dao.TripulacionDAO;
import utils.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class TripulacionController {
    
    TripulacionDAO dao = new TripulacionDAO();

    public boolean guardar(Tripulacion t){

        if(t.getLicencia().isEmpty()){

            return false;
        }

        return dao.guardar(t);
    }

    public List<Tripulacion>
            listarTripulacion() {

        List<Tripulacion> lista
                = new ArrayList<>();

        String sql
                = "SELECT * FROM tripulacion";

        try (
                PreparedStatement stmt
                = DatabaseConnection
                        .getConnection()
                        .prepareStatement(sql)) {

                    ResultSet rs
                            = stmt.executeQuery();

                    while (rs.next()) {

                        lista.add(
                                mapResultSetToTripulacion(rs)
                        );
                    }

                } catch (SQLException e) {

                    System.err.println(
                            "Error al listar tripulación: "
                            + e.getMessage()
                    );
                }

                return lista;
    }
            
    private Tripulacion
            mapResultSetToTripulacion(
                    ResultSet rs)
            throws SQLException {

        Tripulacion t
                = new Tripulacion();

        t.setIdTripulante(
                rs.getInt("id_tripulante")
        );

        t.setNombre(
                rs.getString("nombre")
        );

        t.setRol(
                rs.getString("rol")
        );

        t.setLicencia(
                rs.getString("licencia")
        );

        return t;
    }
}
