/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Aeronave;
import Modelo.Tripulacion;
import utils.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import modelo.Vuelo;

/**
 *
 * @author admin
 */
public class VueloTripulacionController {
    
    public boolean guardarAsignacion(
        Vuelo vuelo,
        Tripulacion piloto,
        Tripulacion copiloto,
        List<Tripulacion> asistentes
) {

    String sql = "INSERT INTO vuelo_tripulacion "
            + "(codigo_vuelo, id_tripulante) "
            + "VALUES (?, ?)";

    try (
            java.sql.Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setString(1, vuelo.getCodigo());
        ps.setInt(2, piloto.getIdTripulante());
        ps.executeUpdate();

        ps.setString(1, vuelo.getCodigo());
        ps.setInt(2, copiloto.getIdTripulante());
        ps.executeUpdate();

        for (Tripulacion t : asistentes) {

            ps.setString(1, vuelo.getCodigo());
            ps.setInt(2, t.getIdTripulante());

            ps.executeUpdate();
        }

        return true;

    } catch (SQLException e) {

        System.out.println(
                "Error guardando asignación: "
                + e.getMessage()
        );

        return false;
    }
}
    public String validarAsignacion(
            Aeronave aeronave,
            Tripulacion piloto,
            Tripulacion copiloto,
            List<Tripulacion> asistentes
    ) {

        if (aeronave == null) {
            return "Seleccione una aeronave";
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

        return null;
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
