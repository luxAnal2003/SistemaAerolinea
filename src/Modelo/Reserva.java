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
    private int idCliente;
    private int idVuelo;
    private int cantidadPasajeros;
    private double total;

    public Reserva() {
    }

    public Reserva(int idCliente, int idVuelo, int cantidadPasajeros, double total) {
        this.idCliente = idCliente;
        this.idVuelo = idVuelo;
        this.cantidadPasajeros = cantidadPasajeros;
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
