/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author admin
 */
public class PasajeroExtra {
    private int id;
    private int idReserva;
    private String nombre;
    private String identificacion;
    private String fechaNacimiento;
    private String asiento;

    
    public PasajeroExtra() {}
    
    public PasajeroExtra(int id, int idReserva, String nombre, String identificacion, String fechaNacimiento, String asiento) {
        this.id = id;
        this.idReserva = idReserva;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.fechaNacimiento = fechaNacimiento;
        this.asiento = asiento;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

}
