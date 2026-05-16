/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

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
public class AeronaveController {
    
    public boolean crearAeronave(Aeronave aeronave) {
        if (!validarDatosAeronave(aeronave)) {
            System.err.println("Error: Datos de aeronave inválidos");
            return false;
        }
        String sql =
                "INSERT INTO aeronaves "
                + "(modelo, capacidad, estado) "
                + "VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1,aeronave.getModelo() );
            stmt.setInt(2, aeronave.getCapacidad() );
            stmt.setString(  3,aeronave.getEstado()
            );
            int resultado =stmt.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.err.println( "Error al crear aeronave: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarAeronave(Aeronave aeronave,int idAero) {
        if (!validarDatosAeronave(aeronave)) {
            System.err.println( "Error: Datos de aeronave inválidos");
            return false;
        }
        if (aeronave.getEstado().equals("Mantenimiento")|| aeronave.getEstado().equals("Inactivo")) {
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
        try (PreparedStatement stmt = DatabaseConnection.getConnection() .prepareStatement(sql)) {
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
    
    private boolean tieneVuelosFuturos( int idAero) {
        String sql//no seria vuelo sino vuelo tripulantes--- verificar
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
    
    private boolean validarDatosAeronave(Aeronave aeronave) {
        if (aeronave == null) {
            return false;
        }
        if (aeronave.getModelo() == null|| aeronave.getModelo().trim().isEmpty()) {
            System.err.println("El modelo es obligatorio");
            return false;
        }
        if (aeronave.getCapacidad() <= 0) {
            System.err.println("La capacidad debe ser mayor a 0" );
            return false;
        }
        if (aeronave.getEstado() == null || aeronave.getEstado().trim().isEmpty()) {
            System.err.println("El estado es obligatorio"  );
            return false;
        }
        return true;
    }
    
    public List<Aeronave> buscarAeronave( String criterio) {
        List<Aeronave> aeronaves= new ArrayList<>();
        String sql
                = "SELECT id_aeronave, modelo, capacidad, estado "
                + "FROM aeronaves "
                + "WHERE LOWER(modelo) LIKE ? "
                + "OR CAST(capacidad AS CHAR) LIKE ? "
                + "OR LOWER(estado) LIKE ?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement stmt= con.prepareStatement(sql)) {
            String busquedaLike = "%" + criterio.toLowerCase() + "%";
            stmt.setString(1, busquedaLike);
            stmt.setString(2, busquedaLike);
            stmt.setString(3, busquedaLike);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aeronave aeronave= new Aeronave();
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
            System.err.println( "Error SQL al obtener aeronaves: "+ e.getMessage());
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
                System.err.println("Error al cerrar conexión: "+ e.getMessage() );
            }
        }
        return aeronaves;
    }
    
    public Aeronave obtenerAeronavePorId( int idAeronave) {
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
                aeronave.setModelo(rs.getString("modelo") );
                aeronave.setCapacidad(rs.getInt("capacidad"));
                aeronave.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.err.println( "Error SQL al obtener aeronave por ID: " + e.getMessage() );
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
}
