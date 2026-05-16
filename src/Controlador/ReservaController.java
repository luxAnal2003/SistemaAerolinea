/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.PasajeroExtra;
import Modelo.Reserva;
import utils.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservaController {

    private Connection con;

    public ReservaController() {
        con = DatabaseConnection.getConnection();
    }

    public int crearReserva(Reserva r) throws SQLException {

        String sql = "INSERT INTO reservas(id_cliente, id_vuelos, cantidad_pasajeros, precio_total, fecha_reserva, estado) VALUES (?, ?, ?, ?, CURDATE(), 'Confirmada')";

        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, r.getIdCliente());
        ps.setInt(2, r.getIdVuelo());
        ps.setInt(3, r.getCantidadPasajeros());
        ps.setDouble(4, r.getTotal());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public boolean guardarPasajero(
            PasajeroExtra p,
            int idCliente,
            int idVuelo
    ) {

        try {

            String sql = "INSERT INTO pasajeros_extra "
                    + "(id_cliente, id_vuelo, nombre, identificacion, fecha_nacimiento) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);
            ps.setInt(2, idVuelo);
            ps.setString(3, p.getNombre());
            ps.setString(4, p.getIdentificacion());

            // FORMATO YYYY-MM-DD
            ps.setDate(
                    5,
                    java.sql.Date.valueOf(p.getFechaNacimiento())
            );

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarAsientoTitular(
            int idReserva,
            String asiento
    ) {

        try {

            String sql = "UPDATE reservas "
                    + "SET asiento = ? "
                    + "WHERE id_reserva = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, asiento);
            ps.setInt(2, idReserva);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarAsientoPasajero(
            String identificacion,
            String asiento
    ) {

        try {

            String sql = "UPDATE pasajeros_extra "
                    + "SET asiento = ? "
                    + "WHERE identificacion = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, asiento);
            ps.setString(2, identificacion);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}
