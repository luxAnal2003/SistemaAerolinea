/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Modelo.Tripulacion;
import utils.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Locale;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class TripulacionController {

    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }
    
    public boolean crearTripu(Tripulacion trip) {

        if (!validarDatosTrip(trip)) {
            return false;
        }

        String sql
                = "INSERT INTO tripulacion "
                + "(cedula, nombre, apellido, rol, licencia) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, trip.getCedula());
            stmt.setString(2, trip.getNombre());
            stmt.setString(3, trip.getApellido());
            stmt.setString(4, trip.getRol());
            stmt.setString(5, trip.getLicencia());

            int resultado = stmt.executeUpdate();

            if (resultado > 0) {
                mensaje = "Tripulante registrado correctamente.";
                return true;
            }
            return false;
        } catch (SQLException e) {
            mensaje = "Error al crear tripulante: " + e.getMessage();
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
            mensaje = "Los datos del tripulante son inválidos.";
            return false;
        }

        if (trip.getNombre() == null || trip.getNombre().trim().isEmpty()) {
            mensaje = "El nombre es obligatorio";
            return false;
        }

        if (trip.getNombre().trim().length() < 3) {
            mensaje = "El nombre debe tener mínimo 3 caracteres";
            return false;
        }

        if (trip.getNombre().trim().length() > 50) {
            mensaje = "El nombre no puede superar los 50 caracteres";
            return false;
        }

        if (!trip.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            mensaje = "El nombre solo puede contener letras y espacios";
            return false;
        }

        if (trip.getApellido() == null || trip.getApellido().trim().isEmpty()) {
            mensaje = "El apellido es obligatorio";
            return false;
        }

        if (trip.getApellido().trim().length() < 3) {
            mensaje = "El apellido debe tener mínimo 3 caracteres";
            return false;
        }

        if (trip.getApellido().trim().length() > 50) {
            mensaje = "El apellido no puede superar los 50 caracteres";
            return false;
        }

        if (!trip.getApellido().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            mensaje = "El apellido solo puede contener letras y espacios";
            return false;
        }

        if (trip.getCedula() == null || trip.getCedula().trim().isEmpty()) {
            mensaje = "La cédula es obligatoria";
            return false;
        }

        if (trip.getCedula().length() < 10) {
            mensaje = "La cédula debe contener 10 dígitos";
            return false;
        }

        if (trip.getCedula().length() > 10) {
            mensaje = "La cédula debe contener exactamente 10 dígitos";
            return false;
        }

        if (!trip.getCedula().matches("\\d+")) {
            mensaje = "La cédula solo puede contener números";
            return false;
        }

        if (existeCedula(trip.getCedula())) {
            mensaje = "Ya existe un tripulante con esa cédula";
            return false;
        }

        if (trip.getLicencia() == null || trip.getLicencia().trim().isEmpty()) {
            mensaje = "La licencia es obligatoria";
            return false;
        }

        if (trip.getLicencia().length() < 5) {
            mensaje = "La licencia debe tener al menos 5 caracteres";
            return false;
        }

        if (trip.getLicencia().length() > 15) {
            mensaje = "La licencia no puede superar los 15 caracteres";
            return false;
        }

        if (!trip.getLicencia().matches("^[a-zA-Z0-9]+$")) {
            mensaje = "La licencia solo puede contener letras y números";
            return false;
        }

        if (existeLicencia(trip.getLicencia())) {
            mensaje = "Ya existe un tripulante con esa licencia";
            return false;
        }

        if (trip.getRol() == null
                || trip.getRol().trim().isEmpty()
                || trip.getRol().equalsIgnoreCase("Seleccione...")){

            mensaje = "Debe seleccionar un rol";
            return false;
        }

        mensaje = "";
        return true;
    }

    private boolean existeCedula(String cedula) {
        String sql
                = "SELECT id_tripulante "
                + "FROM tripulacion "
                + "WHERE cedula = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
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
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar licencia: " + e.getMessage());

            return false;
        }
    }

    public List<Tripulacion> listarTripulacion() {
        List<Tripulacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM tripulacion";

        try (PreparedStatement stmt
                = DatabaseConnection.getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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

        try (Connection con = DatabaseConnection.getConnection();PreparedStatement stmt = con.prepareStatement(sql);ResultSet rs = stmt.executeQuery()) {

            String busquedaLike = "%" + criterio.toLowerCase(Locale.ROOT) + "%";

            stmt.setString(1, busquedaLike);
            stmt.setString(2, busquedaLike);
            stmt.setString(3, busquedaLike);
            stmt.setString(4, busquedaLike);
            stmt.setString(5, busquedaLike);

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
