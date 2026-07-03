/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Controlador;

import controlador.AeronaveController;
import Modelo.Aeronave;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class AeronaveControllerTest {
    
    public AeronaveControllerTest() {
    }

    @Test
    public void testCrearAeronave() {
        System.out.println("crearAeronaveError");

        AeronaveController instance = new AeronaveController();

        Aeronave aeronave = new Aeronave();
        aeronave.setModelo("");
        aeronave.setCapacidad(0);
        aeronave.setEstado("");

        boolean result = instance.crearAeronave(aeronave);

        assertFalse(result);
    }

    @Test
    public void testActualizarAeronave() {
        System.out.println("actualizarAeronave");
        Aeronave aeronave = null;
        int idAero = 0;
        AeronaveController instance = new AeronaveController();
        boolean expResult = false;
        boolean result = instance.actualizarAeronave(aeronave, idAero);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testBuscarAeronave() {
        System.out.println("buscarAeronave");
        String criterio = "xxxxx";
        AeronaveController instance = new AeronaveController();
        List<Aeronave> result = instance.buscarAeronave(criterio);
        assertNotNull(result);
    }

    @Test
    public void testListarAeronaves() {
        System.out.println("listarAeronaves");
        AeronaveController instance = new AeronaveController();
        List<Aeronave> expResult = null;
        List<Aeronave> result = instance.listarAeronaves();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testObtenerAeronavePorId() {
        System.out.println("obtenerAeronavePorId");
        int idAeronave = 0;
        AeronaveController instance = new AeronaveController();
        Aeronave expResult = null;
        Aeronave result = instance.obtenerAeronavePorId(idAeronave);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
