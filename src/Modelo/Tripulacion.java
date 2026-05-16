/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author admin
 */
public class Tripulacion {
    
    private int idTripulante;
    private String cedula;
    private String nombre;
    private String apellido;
    private String rol;
    private String licencia;

    public Tripulacion() {
    }

    public Tripulacion(int idTripulante,
                       String cedula,
                       String nombre,
                       String apellido,
                       String rol,
                       String licencia) {

        this.idTripulante = idTripulante;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.licencia = licencia;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getIdTripulante() {
        return idTripulante;
    }

    public void setIdTripulante(int idTripulante) {
        this.idTripulante = idTripulante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
}
