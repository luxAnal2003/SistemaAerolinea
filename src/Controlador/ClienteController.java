package controlador;

import modelo.Cliente;
import utils.DatabaseConnection;
import utils.Validator;

import java.sql.*;

public class ClienteController {
    
    // Crear nuevo cliente (registro)
    public boolean crearCliente(Cliente cliente) {
        // Validar datos
        if (!validarDatosCliente(cliente)) {
            System.err.println("Error: Datos de cliente inválidos");
            return false;
        }
        
        String sql = "INSERT INTO clientes (cedula, nombres, apellidos, email, password, celular) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cliente.getCedula());
            stmt.setString(2, cliente.getNombres());
            stmt.setString(3, cliente.getApellidos());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getPassword());
            stmt.setString(6, cliente.getCelular());
            
            int resultado = stmt.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                if (e.getMessage().contains("cedula")) {
                    System.err.println("Error: La cédula ya está registrada");
                } else if (e.getMessage().contains("email")) {
                    System.err.println("Error: El correo electrónico ya está registrado");
                }
            } else {
                System.err.println("Error al crear cliente: " + e.getMessage());
            }
            return false;
        }
    }
    
    // Validar login
    public Cliente validarLogin(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        
        String sql = "SELECT * FROM clientes WHERE email = ? AND password = ? AND activo = TRUE";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null;
    }
    
    // Obtener cliente por ID
    public Cliente getClienteById(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ? AND activo = TRUE";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente: " + e.getMessage());
        }
        return null;
    }
    
    // Obtener cliente por email
    public Cliente getClienteByEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ? AND activo = TRUE";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCliente(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente: " + e.getMessage());
        }
        return null;
    }
    
    // Actualizar cliente (nombres, apellidos, email, password, celular)
    public boolean actualizarCliente(Cliente cliente) {
        // Validar datos (sin cédula porque no se edita)
        if (!validarDatosClienteSinCedula(cliente)) {
            System.err.println("Error: Datos inválidos para actualizar");
            return false;
        }
        
        // Verificar si el email ya existe en otro cliente
        if (emailExisteEnOtroCliente(cliente.getEmail(), cliente.getId())) {
            System.err.println("Error: El correo ya está registrado por otro usuario");
            return false;
        }
        
        String sql = "UPDATE clientes SET nombres = ?, apellidos = ?, email = ?, password = ?, celular = ? WHERE id = ?";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombres());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getPassword());
            stmt.setString(5, cliente.getCelular());
            stmt.setInt(6, cliente.getId());
            
            int resultado = stmt.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
    
    // Verificar si el email existe en otro cliente (excluyendo el actual)
    private boolean emailExisteEnOtroCliente(String email, int idActual) {
        String sql = "SELECT id FROM clientes WHERE email = ? AND id != ? AND activo = TRUE";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setInt(2, idActual);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar email: " + e.getMessage());
            return false;
        }
    }
    
    // Eliminar cliente (borrado lógico)
    public boolean eliminarCliente(int id) {
        // Verificar si tiene reservas futuras
        if (tieneReservasActivas(id)) {
            System.err.println("No se puede eliminar: El cliente tiene viajes pendientes");
            return false;
        }
        
        String sql = "UPDATE clientes SET activo = FALSE WHERE id = ?";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            int resultado = stmt.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
    
    // Verificar si tiene reservas futuras activas
    private boolean tieneReservasActivas(int clienteId) {
        String sql = "SELECT id FROM reservas WHERE cliente_id = ? AND fecha_vuelo > CURDATE() AND estado != 'cancelada'";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            boolean tieneReservas = rs.next();
            
            if (tieneReservas) {
                System.out.println("Cliente tiene reservas pendientes, no se puede eliminar");
            }
            return tieneReservas;
            
        } catch (SQLException e) {
            System.err.println("Error al verificar reservas: " + e.getMessage());
            return false; // Si hay error, asumimos que no tiene reservas para no bloquear
        }
    }
    
    // Verificar si un email ya existe (para registro)
    public boolean emailExiste(String email) {
        String sql = "SELECT id FROM clientes WHERE email = ? AND activo = TRUE";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar email: " + e.getMessage());
            return false;
        }
    }
    
    // Verificar si una cédula ya existe
    public boolean cedulaExiste(String cedula) {
        String sql = "SELECT id FROM clientes WHERE cedula = ? AND activo = TRUE";
        
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar cédula: " + e.getMessage());
            return false;
        }
    }
    
    // Convertir ResultSet a objeto Cliente
    private Cliente mapResultSetToCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setCedula(rs.getString("cedula"));
        cliente.setNombres(rs.getString("nombres"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setEmail(rs.getString("email"));
        cliente.setPassword(rs.getString("password"));
        cliente.setCelular(rs.getString("celular"));
        cliente.setFechaUltimaModificacion(rs.getTimestamp("fecha_ultima_modificacion"));
        cliente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        cliente.setActivo(rs.getBoolean("activo"));
        return cliente;
    }
    
    // Validar todos los datos de un cliente (incluyendo cédula)
    private boolean validarDatosCliente(Cliente cliente) {
        return Validator.validarCedula(cliente.getCedula()) &&
               Validator.validarNombre(cliente.getNombres()) &&
               Validator.validarNombre(cliente.getApellidos()) &&
               Validator.validarEmail(cliente.getEmail()) &&
               Validator.validarPassword(cliente.getPassword()) &&
               Validator.validarCelular(cliente.getCelular());
    }
    
    // Validar datos sin cédula (para actualización)
    private boolean validarDatosClienteSinCedula(Cliente cliente) {
        return Validator.validarNombre(cliente.getNombres()) &&
               Validator.validarNombre(cliente.getApellidos()) &&
               Validator.validarEmail(cliente.getEmail()) &&
               Validator.validarPassword(cliente.getPassword()) &&
               Validator.validarCelular(cliente.getCelular());
    }
}