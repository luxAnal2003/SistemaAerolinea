/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Reserva;
import utils.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservaController {

    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public String generarIdReserva() {

        String sql = "SELECT COUNT(*) FROM reservas";

        try (
                Connection con = DatabaseConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {

                int num = rs.getInt(1) + 1001;

                return "RES-" + num;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return "RES-1001";
    }

    public boolean crearReserva(Reserva r) {

        try (
                Connection con = DatabaseConnection.getConnection()) {

            String sqlVuelo
                    = "SELECT cupos, precio_base "
                    + "FROM vuelos "
                    + "WHERE codigo=? "
                    + "AND estado='Activo'";

            PreparedStatement psVuelo
                    = con.prepareStatement(sqlVuelo);

            psVuelo.setString(1, r.getCodigoVuelo());

            ResultSet rs = psVuelo.executeQuery();

            if (!rs.next()) {

                mensaje = "Vuelo no disponible";

                return false;
            }

            int cupos = rs.getInt("cupos");

            double precioBase
                    = rs.getDouble("precio_base");

            if (cupos <= 0) {

                mensaje = "No hay cupos disponibles";

                return false;
            }

            if (r.getCantidadPasajeros() > cupos) {

                mensaje = "Cantidad supera cupos";

                return false;
            }

            double total
                    = precioBase
                    * r.getCantidadPasajeros();

            r.setPrecioTotal(total);

            String fecha = new SimpleDateFormat(
                    "yyyy-MM-dd"
            ).format(new Date());

            r.setFechaReserva(fecha);

            r.setEstado("Reservado");

            String sql
                    = "INSERT INTO reservas "
                    + "VALUES(?,?,?,?,?,?,?)";

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setString(1, r.getIdReserva());
            ps.setInt(2, r.getIdCliente());
            ps.setString(3, r.getCodigoVuelo());
            ps.setInt(4, r.getCantidadPasajeros());
            ps.setDouble(5, r.getPrecioTotal());
            ps.setString(6, r.getFechaReserva());
            ps.setString(7, r.getEstado());

            int filas = ps.executeUpdate();

            String update
                    = "UPDATE vuelos "
                    + "SET cupos = cupos - ? "
                    + "WHERE codigo=?";

            PreparedStatement psUpdate
                    = con.prepareStatement(update);

            psUpdate.setInt(
                    1,
                    r.getCantidadPasajeros());

            psUpdate.setString(
                    2,
                    r.getCodigoVuelo());

            psUpdate.executeUpdate();

            mensaje = "Reserva creada";

            return filas > 0;

        } catch (SQLException e) {

            mensaje = e.getMessage();

            return false;
        }
    }
}