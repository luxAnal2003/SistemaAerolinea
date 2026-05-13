package controlador;

import modelo.Cliente;

public class LoginController {
    private static Cliente clienteActual = null;
    
    // Obtener cliente actualmente logueado
    public static Cliente getClienteActual() {
        return clienteActual;
    }
    
    // Establecer cliente logueado
    public static void setClienteActual(Cliente cliente) {
        clienteActual = cliente;
    }
    
    // Cerrar sesión
    public static void cerrarSesion() {
        clienteActual = null;
    }
    
    // Verificar si hay una sesión activa
    public static boolean haySesionActiva() {
        return clienteActual != null;
    }
}