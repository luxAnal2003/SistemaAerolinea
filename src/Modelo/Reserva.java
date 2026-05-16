/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author admin
 */

public class Reserva {

    private String idReserva;
    private int idCliente;
    private String codigoVuelo;
    private int cantidadPasajeros;
    private double precioTotal;
    private String fechaReserva;
    private String estado;

    public Reserva() {
    }

    public Reserva(
            String idReserva,
            int idCliente,
            String codigoVuelo,
            int cantidadPasajeros,
            double precioTotal,
            String fechaReserva,
            String estado
    ) {

        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.codigoVuelo = codigoVuelo;
        this.cantidadPasajeros = cantidadPasajeros;
        this.precioTotal = precioTotal;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
