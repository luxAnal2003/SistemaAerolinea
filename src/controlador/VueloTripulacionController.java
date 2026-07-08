/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Modelo.Aeronave;
import Modelo.Tripulacion;
import utils.DatabaseConnection;
import java.util.List;
import modelo.Vuelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class VueloTripulacionController {
    public boolean guardarAsignacion(Vuelo vuelo,Tripulacion piloto,Tripulacion copiloto, List<Tripulacion> asistentes) {
        String sql = "INSERT INTO vuelo_tripulacion "
                + "(id_vuelo,id_tripulante) "
                + "VALUES (?,?)";
        Connection con = null;

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, vuelo.getIdVuelo());
            ps.setInt(2, piloto.getIdTripulante());
            ps.executeUpdate();

            ps.setInt(1, vuelo.getIdVuelo());
            ps.setInt(2, copiloto.getIdTripulante());
            ps.executeUpdate();

            for (Tripulacion t : asistentes) {
                ps.setInt(1, vuelo.getIdVuelo());
                ps.setInt(2, t.getIdTripulante());
                ps.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String validarAsignacion(
            Vuelo vuelo,
            Aeronave aeronave,
            Tripulacion piloto,
            Tripulacion copiloto,
            List<Tripulacion> asistentes
    ) {

        if (aeronave == null) {
            return "Seleccione una aeronave";
        }

        if (vuelo == null) {
            return "Debe seleccionar un vuelo";
        }
        
        if (piloto == null) {
            return "Debe seleccionar un piloto";
        }

        if (copiloto == null) {
            return "Debe seleccionar un copiloto";
        }

        if (asistentes == null || asistentes.size() < 5) {
            return "Debe seleccionar mínimo 5 asistentes";
        }

        if (asistentes.size() > 5) {
            return "Solo puede seleccionar máximo 5 asistentes";
        }

        if (aeronave.getEstado().equalsIgnoreCase("Mantenimiento")) {
            return "La aeronave está en mantenimiento";
        }

        if (!aeronave.getEstado().equalsIgnoreCase("Activo")) {
            return "La aeronave no se encuentra activa.";
        }

        if (vueloYaTieneTripulacion(vuelo.getIdVuelo())) {
            return "Este vuelo ya tiene una tripulación asignada.";
        }
        return null;
    }


    private boolean vueloYaTieneTripulacion(int idVuelo) {

        String sql
                = "SELECT COUNT(*) "
                + "FROM vuelo_tripulacion "
                + "WHERE id_vuelo=?";

        try (
                java.sql.Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, idVuelo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public String generarResumen(
            Vuelo vuelo,
            Aeronave aeronave,
            Tripulacion piloto,
            Tripulacion copiloto,
            List<Tripulacion> asistentes
    ) {

        StringBuilder sb = new StringBuilder();

        sb.append("=== ASIGNACIÓN OPERATIVA ===\n\n");

        sb.append("Vuelo: ")
                .append(vuelo.getCodigo())
                .append("\n");

        sb.append("Ruta: ")
                .append(vuelo.getOrigen())
                .append(" → ")
                .append(vuelo.getDestino())
                .append("\n\n");

        sb.append("Aeronave: ")
                .append(aeronave.getModelo())
                .append("\n\n");

        sb.append("Piloto: ")
                .append(piloto.getNombre())
                .append(" ")
                .append(piloto.getApellido())
                .append("\n");

        sb.append("Copiloto: ")
                .append(copiloto.getNombre())
                .append(" ")
                .append(copiloto.getApellido())
                .append("\n\n");

        sb.append("Asistentes:\n");

        for (Tripulacion t : asistentes) {

            sb.append("- ")
                    .append(t.getNombre())
                    .append(" ")
                    .append(t.getApellido())
                    .append("\n");
        }

        return sb.toString();
    }
}
