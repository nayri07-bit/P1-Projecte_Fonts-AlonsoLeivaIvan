package servelet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import p1.t4.daooracle.DAOUser;
import p1.t4.daooracle.OracleConnection;

public class CrearUsuariPredefinit {
    public static void main(String[] args) {
        String username = "admin";
        String passwordPla = "admin"; 
        String hashed = DAOUser.hashPassword(passwordPla);

        String sql = "INSERT INTO usuarios (id, username, password) VALUES (1, ?, ?)";

        try (Connection conn = OracleConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashed);
            ps.executeUpdate();
            System.out.println("Usuari creat correctament");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
