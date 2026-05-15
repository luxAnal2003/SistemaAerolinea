/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import utils.DatabaseConnection;

/**
 *
 * @author admin
 */
public class VueloDAO {
    public boolean asignarAeronave(int idVuelo,int idAeronave) {
        String sql = "UPDATE vuelos "
                   + "SET id_aeronave=? "
                   + "WHERE id_vuelo=?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)){
            stmt.setInt(1, idAeronave);
            stmt.setInt(2, idVuelo);

            stmt.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(e);

            return false;
        }
    }
}
