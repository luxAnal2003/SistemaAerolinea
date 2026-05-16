package controlador;

import modelo.Vuelo;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueloController {

    public boolean registrar(Vuelo v) {
        String sql = "INSERT INTO vuelos VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getCodigo());
            ps.setString(2, v.getAerolinea());
            ps.setString(3, v.getOrigen());
            ps.setString(4, v.getDestino());
            ps.setString(5, v.getFechaSalida());
            ps.setString(6, v.getHoraSalida());
            ps.setString(7, v.getHoraLlegada());
            ps.setInt(8, v.getCupos());
            ps.setString(9, v.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Vuelo> buscarPorCodigo(String codigo) {

        List<Vuelo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vuelos WHERE codigo LIKE ?";

        Connection con = DatabaseConnection.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + codigo + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                lista.add(new Vuelo(
                        rs.getString("codigo"),
                        rs.getString("aerolinea"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("fecha_salida"),
                        rs.getString("hora_salida"),
                        rs.getString("hora_llegada"),
                        rs.getInt("cupos"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {

            System.out.println("Error búsqueda: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizar(Vuelo v) {

        String sql = "UPDATE vuelos SET aerolinea=?, origen=?, destino=?, fecha_salida=?, "
                + "hora_salida=?, hora_llegada=?, cupos=?, estado=? WHERE codigo=?";

        Connection con = utils.DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getAerolinea());
            ps.setString(2, v.getOrigen());
            ps.setString(3, v.getDestino());
            ps.setString(4, v.getFechaSalida());
            ps.setString(5, v.getHoraSalida());
            ps.setString(6, v.getHoraLlegada());
            ps.setInt(7, v.getCupos());
            ps.setString(8, v.getEstado());
            ps.setString(9, v.getCodigo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelar(String codigo) {
        String sql = "UPDATE vuelos SET estado='Cancelado' WHERE codigo=?";
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al cancelar: " + e.getMessage());
            return false;
        }
    }

    public boolean existeCodigo(String codigo) {

        String sql = "SELECT codigo FROM vuelos WHERE codigo = ?";

        Connection con = DatabaseConnection.getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            System.err.println("Error verificando código: " + e.getMessage());
            return false;
        }
    }

    public List<Vuelo> buscarVuelo(String criterio) {

        List<Vuelo> vuelos = new ArrayList<>();

        String sql
                = "SELECT * FROM vuelos "
                + "WHERE LOWER(codigo) LIKE ? "
                + "OR LOWER(aerolinea) LIKE ? "
                + "OR LOWER(origen) LIKE ? "
                + "OR LOWER(destino) LIKE ? "
                + "OR LOWER(estado) LIKE ?";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            String busquedaLike
                    = "%" + criterio.toLowerCase() + "%";

            stmt.setString(1, busquedaLike);
            stmt.setString(2, busquedaLike);
            stmt.setString(3, busquedaLike);
            stmt.setString(4, busquedaLike);
            stmt.setString(5, busquedaLike);

            ResultSet rs = stmt.executeQuery();

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
                        rs.getString("estado")
                );

                vuelos.add(v);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar vuelos: "
                    + e.getMessage()
            );
        }

        return vuelos;
    }

    public List<Vuelo> listarVuelos() {

        List<Vuelo> vuelos = new ArrayList<>();

        String sql = "SELECT * FROM vuelos";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

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
                        rs.getString("estado")
                );

                vuelos.add(v);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar vuelos: "
                    + e.getMessage()
            );
        }

        return vuelos;
    }
}
