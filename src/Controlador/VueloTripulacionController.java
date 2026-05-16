/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import utils.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class VueloTripulacionController {
    
    public boolean asignarTripulante(
            int idVuelo,
            int idTripulante
    ) {
        if (!tripulanteDisponible(idVuelo, idTripulante)) {

            System.err.println(
                    "El tripulante ya está asignado "
                    + "a un vuelo simultáneo"
            );

            return false;
        }

        String sql =
                "INSERT INTO vuelo_tripulacion "
                + "(id_vuelo, id_tripulante) "
                + "VALUES (?, ?)";

        try (
                PreparedStatement stmt =
                        DatabaseConnection
                                .getConnection()
                                .prepareStatement(sql)
        ) {

            stmt.setInt(1, idVuelo);
            stmt.setInt(2, idTripulante);

            int resultado = stmt.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al asignar tripulación: "
                    + e.getMessage()
            );

            return false;
        }
    }

    private boolean tripulanteDisponible(
            int idVuelo,
            int idTripulante
    ) {

        String sql =
                "SELECT vt.id_asignacion "
                + "FROM vuelo_tripulacion vt "
                + "INNER JOIN vuelos v "
                + "ON vt.id_vuelo = v.id_vuelo "
                + "WHERE vt.id_tripulante = ? "
                + "AND v.fecha_salida = ( "
                + "SELECT fecha_salida "
                + "FROM vuelos "
                + "WHERE id_vuelo = ? "
                + ")";

        try (
                PreparedStatement stmt =
                        DatabaseConnection
                                .getConnection()
                                .prepareStatement(sql)
        ) {

            stmt.setInt(1, idTripulante);
            stmt.setInt(2, idVuelo);

            ResultSet rs = stmt.executeQuery();

            return !rs.next();

        } catch (SQLException e) {

            System.err.println(
                    "Error validando disponibilidad: "
                    + e.getMessage()
            );

            return false;
        }
    }
}
