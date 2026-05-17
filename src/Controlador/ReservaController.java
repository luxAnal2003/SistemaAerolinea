/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.PasajeroExtra;
import Modelo.Reserva;
import utils.DatabaseConnection;

import Modelo.Reserva;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

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

    public List<Reserva> listarReservas() {

        List<Reserva> lista = new ArrayList<>();

        try {

            String sql = "SELECT r.id_reserva, "
                    + "v.origen, "
                    + "v.destino, "
                    + "r.fecha_reserva, "
                    + "r.cantidad_pasajeros, "
                    + "r.precio_total, "
                    + "r.estado, "
                    + "r.asiento "
                    + "FROM reservas r "
                    + "INNER JOIN vuelos v "
                    + "ON r.id_vuelos = v.id_vuelo";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Reserva r = new Reserva();

                r.setIdReserva(rs.getInt("id_reserva"));

                r.setRuta(
                        rs.getString("origen")
                        + " - "
                        + rs.getString("destino")
                );

                r.setFechaReserva(
                        rs.getDate("fecha_reserva").toString()
                );

                r.setCantidadPasajeros(
                        rs.getInt("cantidad_pasajeros")
                );

                r.setTotal(
                        rs.getDouble("precio_total")
                );

                r.setEstado(
                        rs.getString("estado")
                );

                r.setAsiento(
                        rs.getString("asiento")
                );

                lista.add(r);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}
