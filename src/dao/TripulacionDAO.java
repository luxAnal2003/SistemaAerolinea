/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Tripulacion;
import java.sql.PreparedStatement;
import utils.DatabaseConnection;

/**
 *
 * @author admin
 */
public class TripulacionDAO {
    public boolean guardar(Tripulacion t) {

        String sql = "INSERT INTO tripulacion(nombre, rol, licencia)"
                   + " VALUES(?,?,?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, t.getNombre());
            stmt.setString(2, t.getRol());
            stmt.setString(3, t.getLicencia());

            stmt.execute();

            return true;

        } catch (Exception e) {

            System.out.println(e);

            return false;
        }
    }
}
