/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Aeronave;
import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import modelo.Cliente;
import utils.DatabaseConnection;
import utils.Validator;
/**
 *
 * @author admin
 */
public class AeronaveDAO {
  
    public boolean guardar(Aeronave a) {

        String sql = "INSERT INTO aeronaves(modelo, capacidad, estado)"
                   + " VALUES(?,?,?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)){

            stmt.setString(1, a.getModelo());
            stmt.setInt(2, a.getCapacidad());
            stmt.setString(3, a.getEstado());

            stmt.execute();

            return true;

        } catch (Exception e) {

            System.out.println(e);
            return false;
        }
    }
}
