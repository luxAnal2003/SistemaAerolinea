/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Tripulacion;
import utils.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class TripulacionController {

    public boolean crearTripu(Tripulacion trip) {

        if (!validarDatosTrip(trip)) {
            System.err.println("Error: Datos de tripulación inválidos");
            return false;
        }

        String sql
                = "INSERT INTO tripulacion "
                + "(cedula, nombre, apellido, rol, licencia) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement stmt
                = DatabaseConnection
                        .getConnection()
                        .prepareStatement(sql)) {

                    stmt.setString(1, trip.getCedula());
                    stmt.setString(2, trip.getNombre());
                    stmt.setString(3, trip.getApellido());
                    stmt.setString(4, trip.getRol());
                    stmt.setString(5, trip.getLicencia());

                    int resultado = stmt.executeUpdate();

                    return resultado > 0;

                } catch (SQLException e) {

                    System.err.println(
                            "Error al crear tripulante: "
                            + e.getMessage()
                    );

                    return false;
                }
    }

    public boolean actualizarTripu(Tripulacion t) {

        String sql = "UPDATE tripulacion "
                + "SET nombre=?, apellido=?, cedula=?, licencia=?, rol=? "
                + "WHERE id_tripulante=?";

        try (
                Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getCedula());
            ps.setString(4, t.getLicencia());
            ps.setString(5, t.getRol());

            ps.setInt(6, t.getIdTripulante());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error actualizar: " + e.getMessage());

            return false;
        }
    }

    private boolean validarDatosTrip(Tripulacion trip) {
        if (trip == null) {
            return false;
        }

        if (trip.getCedula() == null || trip.getCedula().trim().isEmpty()) {
            System.err.println("La cédula es obligatoria");
            return false;
        }

        if (!trip.getCedula().matches("\\d{10}")) {
            System.err.println("La cédula debe contener exactamente 10 números");
            return false;
        }

        if (existeCedula(trip.getCedula())) {
            System.err.println("Ya existe un tripulante " + "con esa cédula");
            return false;
        }

        if (trip.getNombre() == null || trip.getNombre().trim().isEmpty()) {
            System.err.println("El nombre es obligatorio");
            return false;
        }

        if (trip.getApellido() == null || trip.getApellido().trim().isEmpty()) {
            System.err.println("El apellido es obligatorio");
            return false;
        }

        if (trip.getRol() == null || trip.getRol().trim().isEmpty()) {
            System.err.println("El rol es obligatorio");
            return false;
        }

        if (trip.getLicencia() == null || trip.getLicencia().trim().isEmpty()) {
            System.err.println("La licencia es obligatoria");
            return false;
        }

        if (existeLicencia(trip.getLicencia())) {
            System.err.println("Ya existe un tripulante " + "con esa licencia");
            return false;
        }
        return true;
    }

    private boolean existeCedula(String cedula) {
        String sql
                = "SELECT id_tripulante "
                + "FROM tripulacion "
                + "WHERE cedula = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar cédula: " + e.getMessage());

            return false;
        }
    }

    private boolean existeLicencia(String licencia) {
        String sql
                = "SELECT id_tripulante "
                + "FROM tripulacion "
                + "WHERE licencia = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, licencia);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar licencia: " + e.getMessage());

            return false;
        }
    }

    public List<Tripulacion> listarTripulacion() {
        List<Tripulacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM tripulacion";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToTripulacion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tripulación: " + e.getMessage());
        }
        return lista;
    }

    private Tripulacion mapResultSetToTripulacion(ResultSet rs) throws SQLException {
        Tripulacion t = new Tripulacion();

        t.setIdTripulante(rs.getInt("id_tripulante"));
        t.setNombre(rs.getString("nombre"));
        t.setApellido(rs.getString("apellido"));
        t.setCedula(rs.getString("cedula"));
        t.setRol(rs.getString("rol"));
        t.setLicencia(rs.getString("licencia"));

        return t;
    }

    public List<Tripulacion> buscarTripulacion(String criterio) {

        List<Tripulacion> tripulacion = new ArrayList<>();

        String sql
                = "SELECT id_tripulante, cedula, nombre, apellido, rol, licencia "
                + "FROM tripulacion "
                + "WHERE LOWER(nombre) LIKE ? "
                + "OR LOWER(apellido) LIKE ? "
                + "OR LOWER(rol) LIKE ? "
                + "OR LOWER(licencia) LIKE ? "
                + "OR cedula LIKE ?";

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

                Tripulacion trip = new Tripulacion();

                trip.setIdTripulante(
                        rs.getInt("id_tripulante")
                );

                trip.setCedula(
                        rs.getString("cedula")
                );

                trip.setNombre(
                        rs.getString("nombre")
                );

                trip.setApellido(
                        rs.getString("apellido")
                );

                trip.setRol(
                        rs.getString("rol")
                );

                trip.setLicencia(
                        rs.getString("licencia")
                );

                tripulacion.add(trip);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error SQL al buscar tripulación: "
                    + e.getMessage()
            );
        }
        return tripulacion;
    }
}
