package servelet;
import java.sql.*;

import java.sql.Connection;
import java.util.List;
import p1.t4.daooracle.DAOComponentOracle;
import p1.t4.daooracle.DAOItemOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Component;
import p1.t4.model.Item;

public class TestComponentDAO {

    public static void main(String[] args) {

        // Parámetros de conexión
        String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
        String user = "ALUMNE";
        String password = "alumne";

        String sql = "SELECT it_codi, it_tipus, it_nom, it_desc, it_stock FROM item";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int count = 0;
            while (rs.next()) {
                int codi = rs.getInt("it_codi");
                String tipus = rs.getString("it_tipus");
                String nom = rs.getString("it_nom");
                String desc = rs.getString("it_desc");
                int stock = rs.getInt("it_stock");

                System.out.printf("Código: %d | Tipo: %s | Nombre: %s | Descripción: %s | Stock: %d%n",
                        codi, tipus, nom, desc, stock);
                count++;
            }

            System.out.println("Total de items: " + count);

        } catch (Exception e) {
            System.err.println("Error leyendo items:");
            e.printStackTrace();
        }
    }
}
