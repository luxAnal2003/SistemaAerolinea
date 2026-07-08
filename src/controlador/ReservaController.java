package controlador;

import Modelo.PasajeroExtra;
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
        String sql = "INSERT INTO reservas(id_cliente, id_vuelos, cantidad_pasajeros, precio_total, fecha_reserva, estado) "
                + "VALUES (?, ?, ?, ?, CURDATE(), 'Reservado')";

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

    public boolean asientoDisponible(int idVuelo, String asiento) {
        String sql
                = "SELECT r.asiento "
                + "FROM reservas r "
                + "WHERE r.id_vuelos = ? "
                + "AND r.asiento = ? "
                + "AND r.estado <> 'Cancelado' "
                + "UNION "
                + "SELECT pe.asiento "
                + "FROM pasajeros_extra pe "
                + "INNER JOIN reservas r "
                + "ON pe.id_reserva = r.id_reserva "
                + "WHERE pe.id_vuelo = ? "
                + "AND pe.asiento = ? "
                + "AND r.estado <> 'Cancelado'";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVuelo);
            ps.setString(2, asiento);

            ps.setInt(3, idVuelo);
            ps.setString(4, asiento);

            ResultSet rs = ps.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardarPasajero(PasajeroExtra p, int idCliente, int idVuelo, int idReserva, String asiento) throws SQLException {
        if (!asientoDisponible(idVuelo, asiento)) {
            return false;
        }
        String sql = "INSERT INTO pasajeros_extra("
                + "id_cliente,"
                + "id_vuelo,"
                + "id_reserva,"
                + "nombre,"
                + "identificacion,"
                + "fecha_nacimiento,"
                + "asiento)"
                + "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idCliente);
        ps.setInt(2, idVuelo);
        ps.setInt(3, idReserva);
        ps.setString(4, p.getNombre());
        ps.setString(5, p.getIdentificacion());
        ps.setString(6, p.getFechaNacimiento());
        ps.setString(7, asiento);

        return ps.executeUpdate() > 0;
    }

    private int obtenerIdVueloReserva(int idReserva) throws SQLException {
        String sql = "SELECT id_vuelos "
                + "FROM reservas "
                + "WHERE id_reserva=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idReserva);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id_vuelos");
        }
        return -1;
    }

    public boolean actualizarAsientoTitular(int idReserva, String asiento) throws SQLException {
        int idVuelo = obtenerIdVueloReserva(idReserva);
        if (!asientoDisponible(idVuelo, asiento)) {
            return false;
        }

        String sql = "UPDATE reservas SET asiento=? WHERE id_reserva=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, asiento);
        ps.setInt(2, idReserva);
        return ps.executeUpdate() > 0;
    }

    public boolean actualizarAsientoPasajero(String identificacion, String asiento) {
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

    public List<Reserva> listarReservas(int idCliente) {
        List<Reserva> lista = new ArrayList<>();
        try {
            String sql
                    = "SELECT r.id_reserva, "
                    + "CONCAT(v.origen,' - ',v.destino) AS ruta, "
                    + "r.fecha_reserva, "
                    + "r.cantidad_pasajeros, "
                    + "r.precio_total, "
                    + "r.estado, "
                    + "GROUP_CONCAT(a.asiento SEPARATOR ', ') AS asientos "
                    + "FROM reservas r "
                    + "INNER JOIN vuelos v "
                    + "ON r.id_vuelos = v.id_vuelo "
                    + "LEFT JOIN ( "
                    + "SELECT id_reserva, asiento FROM reservas "
                    + "UNION ALL "
                    + "SELECT id_reserva, asiento FROM pasajeros_extra "
                    + ") a "
                    + "ON r.id_reserva = a.id_reserva "
                    + "WHERE r.id_cliente = ? "
                    + "GROUP BY r.id_reserva";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("id_reserva"));
                r.setRuta(rs.getString("ruta"));

                r.setFechaReserva(rs.getDate("fecha_reserva").toString());

                r.setCantidadPasajeros(rs.getInt("cantidad_pasajeros"));

                r.setTotal(rs.getDouble("precio_total"));

                r.setEstado(rs.getString("estado"));

                r.setAsiento(rs.getString("asientos"));

                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Reserva> filtrarReservas(int idCliente, String ruta, String estado, String fecha) {
        List<Reserva> lista = new ArrayList<>();
        try {
            String sql
                    = "SELECT r.id_reserva, "
                    + "CONCAT(v.origen,' - ',v.destino) AS ruta, "
                    + "r.fecha_reserva, "
                    + "r.cantidad_pasajeros, "
                    + "r.precio_total, "
                    + "r.estado, "
                    + "GROUP_CONCAT(a.asiento SEPARATOR ', ') AS asientos "
                    + "FROM reservas r "
                    + "INNER JOIN vuelos v "
                    + "ON r.id_vuelos=v.id_vuelo "
                    + "LEFT JOIN ( "
                    + "SELECT id_reserva, asiento FROM reservas "
                    + "UNION ALL "
                    + "SELECT id_reserva, asiento FROM pasajeros_extra "
                    + ") a "
                    + "ON r.id_reserva=a.id_reserva "
                    + "WHERE r.id_cliente=? ";

            if (!ruta.equals("Todos")) {
                sql += "AND CONCAT(v.origen,' - ',v.destino)=? ";
            }

            if (!estado.equals("Todos")) {
                sql += "AND r.estado=? ";
            }

            if (!fecha.equals("Todas")) {
                sql += "AND r.fecha_reserva=? ";
            }

            sql += "GROUP BY r.id_reserva";

            PreparedStatement ps = con.prepareStatement(sql);

            int index = 1;

            ps.setInt(index++, idCliente);

            if (!ruta.equals("Todos")) {
                ps.setString(index++, ruta);
            }

            if (!estado.equals("Todos")) {
                ps.setString(index++, estado);
            }

            if (!fecha.equals("Todas")) {
                ps.setString(index++, fecha);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(
                        rs.getInt("id_reserva")
                );

                r.setRuta(
                        rs.getString("ruta")
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
                        rs.getString("asientos")
                );

                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Reserva> buscarReservas(int idCliente, String criterio) {
        List<Reserva> lista = new ArrayList<>();
        try {
            String sql
                    = "SELECT r.id_reserva, "
                    + "CONCAT(v.origen,' - ',v.destino) AS ruta, "
                    + "r.fecha_reserva, "
                    + "r.cantidad_pasajeros, "
                    + "r.precio_total, "
                    + "r.estado, "
                    + "GROUP_CONCAT(a.asiento SEPARATOR ', ') AS asientos "
                    + "FROM reservas r "
                    + "INNER JOIN vuelos v "
                    + "ON r.id_vuelos=v.id_vuelo "
                    + "LEFT JOIN ( "
                    + "SELECT id_reserva, asiento FROM reservas "
                    + "UNION ALL "
                    + "SELECT id_reserva, asiento FROM pasajeros_extra "
                    + ") a "
                    + "ON r.id_reserva=a.id_reserva "
                    + "WHERE r.id_cliente=? "
                    + "AND ( "
                    + "v.origen LIKE ? "
                    + "OR v.destino LIKE ? "
                    + "OR r.estado LIKE ? "
                    + "OR a.asiento LIKE ? "
                    + ") "
                    + "GROUP BY r.id_reserva";

            PreparedStatement ps = con.prepareStatement(sql);
            String filtro = "%" + criterio + "%";
            ps.setInt(1, idCliente);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ps.setString(4, filtro);
            ps.setString(5, filtro);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(
                        rs.getInt("id_reserva")
                );

                r.setRuta(
                        rs.getString("ruta")
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
                        rs.getString("asientos")
                );
                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<String> obtenerRutas(int idCliente) {
        List<String> rutas = new ArrayList<>();
        String sql
                = "SELECT DISTINCT CONCAT(v.origen,' - ',v.destino) AS ruta "
                + "FROM reservas r "
                + "INNER JOIN vuelos v ON r.id_vuelos = v.id_vuelo "
                + "WHERE r.id_cliente = ? "
                + "ORDER BY ruta";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rutas.add(rs.getString("ruta"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rutas;
    }

    public List<String> obtenerEstados(int idCliente) {
        List<String> estados = new ArrayList<>();
        String sql
                = "SELECT DISTINCT r.estado "
                + "FROM reservas r "
                + "WHERE r.id_cliente = ? "
                + "ORDER BY r.estado";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                estados.add(rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return estados;
    }

    public List<String> obtenerFechas(int idCliente) {
        List<String> fechas = new ArrayList<>();
        String sql
                = "SELECT DISTINCT DATE_FORMAT(r.fecha_reserva,'%Y-%m-%d') AS fecha "
                + "FROM reservas r "
                + "WHERE r.id_cliente = ? "
                + "ORDER BY fecha DESC";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fechas.add(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return fechas;
    }

    public boolean existeReserva(int idReserva) {
        String sql = "SELECT id_reserva FROM reservas WHERE id_reserva = ?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean asientoOcupadoTitular(int idReserva, int idVuelo, String asiento) {
        String sql
                = "SELECT r.asiento "
                + "FROM reservas r "
                + "WHERE r.id_vuelos=? "
                + "AND r.asiento=? "
                + "AND r.estado<>'Cancelado' "
                + "AND r.id_reserva<> ? "
                + " UNION "
                + "SELECT pe.asiento "
                + "FROM pasajeros_extra pe "
                + "INNER JOIN reservas r "
                + "ON pe.id_reserva=r.id_reserva "
                + "WHERE pe.id_vuelo=? "
                + "AND pe.asiento=? "
                + "AND r.estado<>'Cancelado'";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVuelo);
            ps.setString(2, asiento);
            ps.setInt(3, idReserva);
            ps.setInt(4, idVuelo);
            ps.setString(5, asiento);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public int obtenerVueloReserva(int idReserva) {
        String sql
                = "SELECT id_vuelos "
                + "FROM reservas "
                + "WHERE id_reserva=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_vuelos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String cambiarAsiento(int idReserva, String nuevoAsiento) {
        if (!existeReserva(idReserva)) {
            return "Reserva no encontrada";
        }
        int idVuelo = obtenerVueloReserva(idReserva);

        if (idVuelo == -1) {
            return "Reserva no encontrada";
        }

        if (asientoOcupadoTitular(idReserva, idVuelo, nuevoAsiento)) {
            return "Asiento no disponible";
        }

        String sql
                = "UPDATE reservas "
                + "SET asiento=? "
                + "WHERE id_reserva=?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoAsiento);
            ps.setInt(2, idReserva);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return "Asiento actualizado correctamente";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error al actualizar asiento";
    }

    public List<PasajeroExtra> obtenerPasajerosReserva(int idReserva) {
        List<PasajeroExtra> lista = new ArrayList<>();
        String sql
                = "SELECT "
                + "CONCAT(c.nombres,' ',c.apellidos) AS nombre, "
                + "c.cedula AS identificacion, "
                + "r.asiento, "
                + "'TITULAR' AS tipo "
                + "FROM reservas r "
                + "INNER JOIN clientes c "
                + "ON r.id_cliente = c.id "
                + "WHERE r.id_reserva=? "
                + "UNION ALL "
                + "SELECT "
                + "pe.nombre, "
                + "pe.identificacion, "
                + "pe.asiento, "
                + "'EXTRA' AS tipo "
                + "FROM pasajeros_extra pe "
                + "WHERE pe.id_reserva=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idReserva);
            ps.setInt(2, idReserva);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PasajeroExtra p = new PasajeroExtra();
                p.setNombre(
                        rs.getString("nombre")
                );

                p.setIdentificacion(
                        rs.getString("identificacion")
                );

                p.setAsiento(
                        rs.getString("asiento")
                );

                p.setTipo(
                        rs.getString("tipo")
                );

                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean asientoOcupadoExtra(String identificacion, int idVuelo, String asiento) {
        String sql
                = "SELECT pe.asiento "
                + "FROM pasajeros_extra pe "
                + "INNER JOIN reservas r "
                + "ON pe.id_reserva=r.id_reserva "
                + "WHERE pe.id_vuelo=? "
                + "AND pe.asiento=? "
                + "AND pe.identificacion<> ? "
                + "AND r.estado<>'Cancelado' "
                + " UNION "
                + "SELECT r.asiento "
                + "FROM reservas r "
                + "WHERE r.id_vuelos=? "
                + "AND r.asiento=? "
                + "AND r.estado<>'Cancelado'";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVuelo);
            ps.setString(2, asiento);
            ps.setString(3, identificacion);

            ps.setInt(4, idVuelo);
            ps.setString(5, asiento);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public String cambiarAsientoPasajero(String identificacion, String nuevoAsiento) {
        try {
            String sql
                    = "SELECT id_vuelo, asiento "
                    + "FROM pasajeros_extra "
                    + "WHERE identificacion=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, identificacion);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return "Pasajero no encontrado";
            }
            int idVuelo = rs.getInt("id_vuelo");
            String asientoAnterior = rs.getString("asiento");

            if (asientoAnterior == null || asientoAnterior.trim().isEmpty()) {
                return "El pasajero no tiene un asiento asignado.";
            }

            if (nuevoAsiento.equals(asientoAnterior)) {
                return "Seleccione un asiento diferente.";
            }

            if (asientoOcupadoExtra(identificacion, idVuelo, nuevoAsiento)) {
                return "Asiento no disponible";
            }

            sql
                    = "UPDATE pasajeros_extra "
                    + "SET asiento=? "
                    + "WHERE identificacion=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoAsiento);
            ps.setString(2, identificacion);
            if (ps.executeUpdate() > 0) {
                return "Asiento actualizado correctamente";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error al actualizar asiento";
    }

    public String cambiarAsientoTitular(int idReserva, String nuevoAsiento) {
        int idVuelo = obtenerVueloReserva(idReserva);
        if (idVuelo == -1) {
            return "Reserva no encontrada";
        }

        if (asientoOcupadoTitular(idReserva, idVuelo, nuevoAsiento)) {
            return "Asiento no disponible";
        }

        String sql
                = "UPDATE reservas "
                + "SET asiento=? "
                + "WHERE id_reserva=?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoAsiento);
            ps.setInt(2, idReserva);
            if (ps.executeUpdate() > 0) {
                return "Asiento actualizado correctamente";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error al actualizar asiento";
    }

    public String desactivarReserva(int idReserva) {
        String sqlBuscar
                = "SELECT id_vuelos, cantidad_pasajeros, estado "
                + "FROM reservas "
                + "WHERE id_reserva = ?";

        String sqlCancelar
                = "UPDATE reservas "
                + "SET estado='Cancelado' "
                + "WHERE id_reserva=?";

        String sqlLiberarTitular
                = "UPDATE reservas "
                + "SET asiento=NULL "
                + "WHERE id_reserva=?";

        String sqlLiberarExtras
                = "UPDATE pasajeros_extra "
                + "SET asiento=NULL "
                + "WHERE id_reserva=?";

        String sqlLiberarCupos
                = "UPDATE vuelos "
                + "SET cupos = cupos + ? "
                + "WHERE id_vuelo=?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);

            PreparedStatement psBuscar = con.prepareStatement(sqlBuscar);
            psBuscar.setInt(1, idReserva);

            ResultSet rs = psBuscar.executeQuery();

            if (!rs.next()) {
                con.rollback();
                return "Reserva no encontrada";
            }

            String estado = rs.getString("estado");

            if (estado.equalsIgnoreCase("Cancelado")) {
                con.rollback();
                return "La reserva ya fue cancelada previamente";
            }

            if (estado.equalsIgnoreCase("Pagado")) {
                con.rollback();
                return "No es posible cancelar una reserva pagada. Según las políticas de la empresa no se realizan reembolsos.";
            }

            int idVuelo = rs.getInt("id_vuelos");
            int pasajeros = rs.getInt("cantidad_pasajeros");

            PreparedStatement psCancelar = con.prepareStatement(sqlCancelar);
            psCancelar.setInt(1, idReserva);
            psCancelar.executeUpdate();

            PreparedStatement psTitular = con.prepareStatement(sqlLiberarTitular);
            psTitular.setInt(1, idReserva);
            psTitular.executeUpdate();

            PreparedStatement psExtras = con.prepareStatement(sqlLiberarExtras);
            psExtras.setInt(1, idReserva);
            psExtras.executeUpdate();

            PreparedStatement psCupos = con.prepareStatement(sqlLiberarCupos);
            psCupos.setInt(1, pasajeros);
            psCupos.setInt(2, idVuelo);
            psCupos.executeUpdate();

            con.commit();

            return "Reserva cancelada correctamente";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cancelar la reserva";
        }
    }

    public boolean reservaCancelada(int idReserva) {
        String sql = "SELECT estado FROM reservas WHERE id_reserva=?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("estado")
                        .equalsIgnoreCase("Cancelado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
