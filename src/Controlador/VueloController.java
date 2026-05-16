package controlador;

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

    // =========================================================
    // REGISTRAR
    // =========================================================
    public boolean registrar(Vuelo v) {

    // VALIDAR AERONAVE
    if (!aeronaveDisponible(v.getIdAeronave())) {

        mensaje = "La aeronave seleccionada no está activa.";

        return false;
    }

    // VALIDAR CÓDIGO DUPLICADO
    if (codigoExiste(v.getCodigo())) {

        mensaje = "Ya existe un vuelo con ese código.";

        return false;
    }

    String sql = "INSERT INTO vuelos "
            + "(codigo, aerolinea, origen, destino, fecha_salida, "
            + "hora_salida, hora_llegada, cupos, estado, id_aeronave) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, v.getCodigo());
        ps.setString(2, v.getAerolinea());
        ps.setString(3, v.getOrigen());
        ps.setString(4, v.getDestino());
        ps.setString(5, v.getFechaSalida());
        ps.setString(6, v.getHoraSalida());
        ps.setString(7, v.getHoraLlegada());
        ps.setInt(8, v.getCupos());
        ps.setString(9, v.getEstado());
        ps.setInt(10, v.getIdAeronave());

        boolean resultado = ps.executeUpdate() > 0;

        if (resultado) {
            mensaje = "Vuelo registrado correctamente.";
        }

        return resultado;

    } catch (SQLException e) {

        mensaje = "Error al registrar vuelo: " + e.getMessage();

        return false;
    }
}
    
    public boolean codigoExiste(String codigo) {

    String sql = "SELECT codigo FROM vuelos WHERE codigo=?";

    try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, codigo);

        ResultSet rs = ps.executeQuery();

        return rs.next();

    } catch (SQLException e) {

        System.out.println("Error validando código: " + e.getMessage());
    }

    return false;
}
    
    // =========================================================
    // ACTUALIZAR
    // =========================================================
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
            + "id_aeronave=? "
            + "WHERE codigo=?";

    try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, v.getAerolinea());
        ps.setString(2, v.getOrigen());
        ps.setString(3, v.getDestino());
        ps.setString(4, v.getFechaSalida());
        ps.setString(5, v.getHoraSalida());
        ps.setString(6, v.getHoraLlegada());
        ps.setInt(7, v.getCupos());
        ps.setString(8, v.getEstado());
        ps.setInt(9, v.getIdAeronave());
        ps.setString(10, v.getCodigo());

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
    // =========================================================
    // CANCELAR
    // =========================================================
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

    // =========================================================
    // LISTAR
    // =========================================================
    public List<Vuelo> listarVuelos() {

        List<Vuelo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vuelos";

        try (
                Connection con = DatabaseConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

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
                        rs.getInt("id_aeronave")
                );

                lista.add(v);
            }

        } catch (SQLException e) {

            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }

    // =========================================================
    // BUSCAR
    // =========================================================
    public List<Vuelo> buscarVuelo(String texto) {

        List<Vuelo> lista = new ArrayList<>();

        String sql
                = "SELECT * FROM vuelos "
                + "WHERE codigo LIKE ? "
                + "OR aerolinea LIKE ?";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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
                        rs.getInt("id_aeronave")
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
}
