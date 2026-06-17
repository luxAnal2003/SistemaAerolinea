package controlador;

import Modelo.Aeronave;
import modelo.Vuelo;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueloController {

    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public boolean registrar(Vuelo v) {

        if (!aeronaveDisponible(v.getIdAeronave())) {
            mensaje = "La aeronave seleccionada no está activa.";
            return false;
        }

        if (codigoExiste(v.getCodigo())) {
            mensaje = "Ya existe un vuelo con ese código.";
            return false;
        }

        String sql = "INSERT INTO vuelos "
                + "(codigo, aerolinea, origen, destino, fecha_salida, "
                + "hora_salida, hora_llegada, cupos, estado, precio_base, id_aeronave) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getCodigo());
            ps.setString(2, v.getAerolinea());
            ps.setString(3, v.getOrigen());
            ps.setString(4, v.getDestino());
            ps.setString(5, v.getFechaSalida());
            ps.setString(6, v.getHoraSalida());
            ps.setString(7, v.getHoraLlegada());
            ps.setInt(8, v.getCupos());
            ps.setString(9, v.getEstado());
            ps.setDouble(10, v.getPrecioBase());
            ps.setInt(11, v.getIdAeronave());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            mensaje = "Error: " + e.getMessage();
            return false;
        }
    }

    public boolean codigoExiste(String codigo) {

        String sql = "SELECT codigo FROM vuelos WHERE codigo=?";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            System.out.println("Error validando código: " + e.getMessage());
        }

        return false;
    }

    public boolean actualizar(Vuelo v) {

        if (!aeronaveDisponible(v.getIdAeronave())) {

            mensaje = "La aeronave seleccionada no está activa.";

            return false;
        }

        String sql = "UPDATE vuelos SET "
                + "aerolinea=?, "
                + "origen=?, "
                + "destino=?, "
                + "fecha_salida=?, "
                + "hora_salida=?, "
                + "hora_llegada=?, "
                + "cupos=?, "
                + "estado=?, "
                + "precio_base=?, "
                + "id_aeronave=? "
                + "WHERE codigo=?";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getAerolinea());
            ps.setString(2, v.getOrigen());
            ps.setString(3, v.getDestino());
            ps.setString(4, v.getFechaSalida());
            ps.setString(5, v.getHoraSalida());
            ps.setString(6, v.getHoraLlegada());
            ps.setInt(7, v.getCupos());
            ps.setString(8, v.getEstado());
            ps.setDouble(9, v.getPrecioBase());
            ps.setInt(10, v.getIdAeronave());
            ps.setString(11, v.getCodigo());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                mensaje = "Vuelo actualizado correctamente.";
            }

            return resultado;

        } catch (SQLException e) {

            mensaje = "Error al actualizar: " + e.getMessage();

            return false;
        }
    }

    public boolean cancelar(String codigo) {

        String sql
                = "UPDATE vuelos "
                + "SET estado='Cancelado' "
                + "WHERE codigo=?";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error cancelar: " + e.getMessage());

            return false;
        }
    }

    public List<Vuelo> listarVuelos() {

        List<Vuelo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vuelos";

        try (Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Vuelo v = new Vuelo(
                        rs.getString("codigo"),
                        rs.getString("aerolinea"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("fecha_salida"),
                        rs.getString("hora_salida"),
                        rs.getString("hora_llegada"),
                        rs.getInt("cupos"),
                        rs.getString("estado"),
                        rs.getDouble("precio_base"),
                        rs.getInt("id_aeronave"),
                        rs.getInt("id_vuelo")
                );

                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }

    public List<Aeronave> listarAeronaves() {

        List<Aeronave> lista = new ArrayList<>();

        String sql = "SELECT * FROM aeronaves";

        try (Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Aeronave a = new Aeronave();
                a.setIdAeronave(rs.getInt("id_aeronave"));
                a.setModelo(rs.getString("modelo"));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar aeronaves: " + e.getMessage());
        }

        return lista;
    }

    public List<Vuelo> buscarVuelo(String texto) {

        List<Vuelo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vuelos WHERE codigo LIKE ? OR aerolinea LIKE ?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Vuelo v = new Vuelo(
                        rs.getString("codigo"),
                        rs.getString("aerolinea"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("fecha_salida"),
                        rs.getString("hora_salida"),
                        rs.getString("hora_llegada"),
                        rs.getInt("cupos"),
                        rs.getString("estado"),
                        rs.getDouble("precio_base"),
                        rs.getInt("id_aeronave"),
                        rs.getInt("id_vuelo")
                );

                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error búsqueda: " + e.getMessage());
        }

        return lista;
    }

    public boolean aeronaveDisponible(int idAeronave) {

        String sql
                = "SELECT estado "
                + "FROM aeronaves "
                + "WHERE id_aeronave=?";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAeronave);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String estado = rs.getString("estado");

                return estado.equalsIgnoreCase("Activo");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error validando aeronave: "
                    + e.getMessage()
            );
        }

        return false;
    }

    public List<Vuelo> filtrarVuelos(String origen, String destino, String fecha) {

        List<Vuelo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vuelos WHERE origen = ? AND destino = ? AND fecha_salida = ?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, origen);
            ps.setString(2, destino);
            ps.setString(3, fecha);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Vuelo v = new Vuelo();

                v.setCodigo(rs.getString("codigo"));
                v.setAerolinea(rs.getString("aerolinea"));
                v.setOrigen(rs.getString("origen"));
                v.setDestino(rs.getString("destino"));
                v.setFechaSalida(rs.getString("fecha_salida"));
                v.setHoraSalida(rs.getString("hora_salida"));
                v.setHoraLlegada(rs.getString("hora_llegada"));
                v.setCupos(rs.getInt("cupos"));
                v.setEstado(rs.getString("estado"));
                v.setPrecioBase(rs.getDouble("precio_base"));
                v.setIdAeronave(rs.getInt("id_aeronave"));

                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error al filtrar vuelos: " + e.getMessage());
        }

        return lista;
    }
    public List<Vuelo> filtrarVuelosFlex(String origen, String destino, String fecha) {

        List<Vuelo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vuelos WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (!origen.equals("Seleccione")) {
            sql += " AND origen=?";
            params.add(origen);
        }

        if (!destino.equals("Seleccione")) {
            sql += " AND destino=?";
            params.add(destino);
        }

        if (!fecha.equals("Seleccione")) {
            sql += " AND fecha_salida=?";
            params.add(fecha);
        }

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Vuelo v = new Vuelo(
                        rs.getString("codigo"),
                        rs.getString("aerolinea"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("fecha_salida"),
                        rs.getString("hora_salida"),
                        rs.getString("hora_llegada"),
                        rs.getInt("cupos"),
                        rs.getString("estado"),
                        rs.getDouble("precio_base"),
                        rs.getInt("id_aeronave"),
                        rs.getInt("id_vuelo")
                );

                lista.add(v);
            }

        } catch (Exception e) {
            System.out.println("Error filtro: " + e.getMessage());
        }

        return lista;
    }

    public List<String> obtenerOrigenes() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT origen FROM vuelos";

        try (Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(rs.getString("origen"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener orígenes: " + e.getMessage());
        }

        return lista;
    }

    public List<String> obtenerDestinos() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT destino FROM vuelos";

        try (Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(rs.getString("destino"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener destinos: " + e.getMessage());
        }

        return lista;
    }

    public List<String> obtenerFechas() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT fecha_salida FROM vuelos ORDER BY fecha_salida";

        try (Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(rs.getString("fecha_salida"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener fechas: " + e.getMessage());
        }

        return lista;
    }
}
