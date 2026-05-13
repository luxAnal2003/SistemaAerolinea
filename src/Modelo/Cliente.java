package modelo;

import java.util.Date;

public class Cliente {
    private int id;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String celular;
    private Date fechaUltimaModificacion;
    private Date fechaRegistro;
    private boolean activo;
    
    // Constructor vacío
    public Cliente() {}
    
    // Constructor con datos básicos
    public Cliente(String cedula, String nombres, String apellidos, 
                   String email, String password, String celular) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.celular = celular;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getCedula() { 
        return cedula; 
    }
    
    public void setCedula(String cedula) { 
        this.cedula = cedula; 
    }
    
    public String getNombres() { 
        return nombres; 
    }
    
    public void setNombres(String nombres) { 
        this.nombres = nombres; 
    }
    
    public String getApellidos() { 
        return apellidos; 
    }
    
    public void setApellidos(String apellidos) { 
        this.apellidos = apellidos; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public String getCelular() { 
        return celular; 
    }
    
    public void setCelular(String celular) { 
        this.celular = celular; 
    }
    
    public Date getFechaUltimaModificacion() { 
        return fechaUltimaModificacion; 
    }
    
    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) { 
        this.fechaUltimaModificacion = fechaUltimaModificacion; 
    }
    
    public Date getFechaRegistro() { 
        return fechaRegistro; 
    }
    
    public void setFechaRegistro(Date fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }
    
    public boolean isActivo() { 
        return activo; 
    }
    
    public void setActivo(boolean activo) { 
        this.activo = activo; 
    }
    
    // Método útil para obtener nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    // Para mostrar información en la interfaz
    @Override
    public String toString() {
        return getNombreCompleto();
    }
}