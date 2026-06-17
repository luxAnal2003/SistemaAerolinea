/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Modelo.Aeronave;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

/**
 *
 * @author admin
 */
public class CDUVAeronaveController {

    private String mensaje;

    /**
     * Devuelve un mensaje a la vista
     *
     * @return El mensaje dependiendo del resultado
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Crea la aeronave
     *
     * @param aeronave Recibe una aeronave a registrar
     * @return El registro correcto o incorrecto
     */
    public boolean crearAeronave(Aeronave aeronave) {
        if (!validarDatosAeronave(aeronave)) {
            return false;
        }
        String sql
                = "INSERT INTO aeronaves "
                + "(modelo, capacidad, estado) "
                + "VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, aeronave.getModelo());
            stmt.setInt(2, aeronave.getCapacidad());
            stmt.setString(3, aeronave.getEstado());
            int resultado = stmt.executeUpdate();

            if (resultado > 0) {
                mensaje = "Aeronave registrada correctamente.";
                return true;
            }
            mensaje = "No se pudo registrar la aeronave.";
            return false;
        } catch (SQLException e) {
            mensaje = "Error al crear aeronave: " + e.getMessage();
            return false;
        }
    }

    /**
     * Actualiza una aeronave
     *
     * @param aeronave Aeronave a actualizar
     * @param idAero Id de la aeronave seleccionada
     * @return True si se actualiza y false si no
     */
    public boolean actualizarAeronave(Aeronave aeronave, int idAero) {
        if (!validarDatosAeronave(aeronave)) {
            System.err.println("Error: Datos de aeronave inválidos");
            return false;
        }
        if (aeronave.getEstado().equals("Mantenimiento") || aeronave.getEstado().equals("Inactivo")) {
            if (tieneVuelosFuturos(idAero)) {
                System.err.println("No se puede cambiar el estado de la aeronave porque tiene "
                        + "vuelos futuros asignados");
                return false;
            }
        }
        String sql
                = "UPDATE aeronaves "
                + "SET modelo=?, capacidad=?, estado=? "
                + "WHERE id_aeronave=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, aeronave.getModelo());
            stmt.setInt(2, aeronave.getCapacidad());
            stmt.setString(3, aeronave.getEstado());
            stmt.setInt(4, idAero);

            int resultado = stmt.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar aeronave: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si una aeronave tiene vuelos programados con fecha de salida
     * posterior a la fecha y hora actual.
     *
     * @param idAero Identificador de la aeronave que se desea verificar.
     * @return si la aeronave tiene al menos un vuelo futuro
     */
    private boolean tieneVuelosFuturos(int idAero) {
        String sql
                = "SELECT COUNT(*) "
                + "FROM vuelos "
                + "WHERE id_aeronave = ? "
                + "AND fecha_salida > NOW()";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idAero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar vuelos futuros: " + e.getMessage());
        }

        return false;
    }

    /**
     * Valida los datos de una aeronave antes de realizar operaciones de
     * registro o actualización.
     *
     * @param aeronave Aeronave cuyos datos serán validados.
     * @return true si todos los datos son válidos y false caso contrario
     */
    private boolean validarDatosAeronave(Aeronave aeronave) {

        if (aeronave == null) {
            mensaje = "La aeronave es nula";
            return false;
        }

        String modelo = aeronave.getModelo();

        if (modelo == null || modelo.trim().isEmpty()) {
            mensaje = "El modelo es obligatorio";
            return false;
        }

        if (aeronave.getEstado() == null || aeronave.getEstado().trim().isEmpty()) {
            mensaje = "El estado es obligatorio";
            return false;
        }

        if (modelo.trim().length() < 3) {
            mensaje = "El modelo debe tener al menos 3 caracteres";
            return false;
        }

        if (modelo.trim().length() > 30) {
            mensaje = "El modelo no puede superar los 30 caracteres";
            return false;
        }

        if (!modelo.matches("^[a-zA-Z0-9 ]+$")) {
            mensaje = "El modelo solo puede contener letras, números y espacios";
            return false;
        }

        if (aeronave.getCapacidad() <= 0) {
            mensaje = "La capacidad debe ser mayor que cero";
            return false;
        }

        if (aeronave.getCapacidad() > 200) {
            mensaje = "La capacidad máxima permitida es 200 pasajeros";
            return false;
        }

        String estado = aeronave.getEstado();

        if (estado == null
                || estado.trim().isEmpty()
                || estado.equalsIgnoreCase("Seleccione...")) {

            mensaje = "Debe seleccionar un estado";
            return false;
        }

        mensaje = "";
        return true;
    }

    public List<Aeronave> buscarAeronave(String criterio) {
        List<Aeronave> aeronaves = new ArrayList<>();
        String sql
                = "SELECT id_aeronave, modelo, capacidad, estado "
                + "FROM aeronaves "
                + "WHERE LOWER(modelo) LIKE ? "
                + "OR CAST(capacidad AS CHAR) LIKE ? "
                + "OR LOWER(estado) LIKE ?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            String busquedaLike = "%" + criterio.toLowerCase() + "%";
            stmt.setString(1, busquedaLike);
            stmt.setString(2, busquedaLike);
            stmt.setString(3, busquedaLike);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aeronave aeronave = new Aeronave();
                aeronave.setIdAeronave(rs.getInt("id_aeronave"));
                aeronave.setModelo(rs.getString("modelo"));
                aeronave.setCapacidad(rs.getInt("capacidad"));
                aeronave.setEstado(rs.getString("estado"));
                aeronaves.add(aeronave);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error SQL al buscar aeronaves: "
                    + e.getMessage()
            );
        }
        return aeronaves;
    }

    public List<Aeronave> listarAeronaves() {
        List<Aeronave> aeronaves = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String sql
                = "SELECT id_aeronave, modelo, capacidad, estado "
                + "FROM aeronaves";

        try {
            con = DatabaseConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Aeronave aeronave = new Aeronave();
                aeronave.setIdAeronave(
                        rs.getInt("id_aeronave")
                );
                aeronave.setModelo(
                        rs.getString("modelo")
                );
                aeronave.setCapacidad(
                        rs.getInt("capacidad")
                );
                aeronave.setEstado(
                        rs.getString("estado")
                );
                aeronaves.add(aeronave);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener aeronaves: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return aeronaves;
    }

    public Aeronave obtenerAeronavePorId(int idAeronave) {
        Aeronave aeronave = null;
        String sql
                = "SELECT id_aeronave, modelo, capacidad, estado "
                + "FROM aeronaves "
                + "WHERE id_aeronave = ?";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, idAeronave);
            rs = pst.executeQuery();
            if (rs.next()) {
                aeronave = new Aeronave();
                aeronave.setIdAeronave(rs.getInt("id_aeronave"));
                aeronave.setModelo(rs.getString("modelo"));
                aeronave.setCapacidad(rs.getInt("capacidad"));
                aeronave.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener aeronave por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return aeronave;
    }

    public String activarAero(int idAeronave) {

        Aeronave aero = obtenerAeronavePorId(idAeronave);

        if (aero == null) {
            return "La aeronave no existe";
        }

        if ("Mantenimiento".equalsIgnoreCase(aero.getEstado())) {
            return "La aeronave se encuentra en mantenimiento y ya está activa.";
        }

        if ("Activo".equalsIgnoreCase(aero.getEstado())) {
            return "La aeronave ya está activa";
        }

        String sql = "UPDATE aeronaves SET estado = 'Activo' WHERE id_aeronave = ?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAeronave);

            int filas = ps.executeUpdate();

            return filas > 0
                    ? "Aeronave activada correctamente"
                    : "Error al activar la aeronave";

        } catch (SQLException e) {
            return "Error al activar la aeronave: " + e.getMessage();
        }
    }

    public String desactivarAero(int idAeronave) {

        Aeronave aero = obtenerAeronavePorId(idAeronave);

        if (aero == null) {
            return "La aeronave no existe";
        }

        if ("Mantenimiento".equalsIgnoreCase(aero.getEstado())) {
            return "No se puede desactivar la aeronave porque se encuentra en mantenimiento.";
        }

        if ("Inactivo".equalsIgnoreCase(aero.getEstado())) {
            return "La aeronave ya está inactiva";
        }

        String sql = "UPDATE aeronaves SET estado = 'Inactivo' WHERE id_aeronave = ?";

        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAeronave);

            int filas = ps.executeUpdate();

            return filas > 0
                    ? "Aeronave desactivada correctamente"
                    : "Error al desactivar la aeronave";

        } catch (SQLException e) {
            return "Error al desactivar la aeronave: " + e.getMessage();
        }
    }
}
