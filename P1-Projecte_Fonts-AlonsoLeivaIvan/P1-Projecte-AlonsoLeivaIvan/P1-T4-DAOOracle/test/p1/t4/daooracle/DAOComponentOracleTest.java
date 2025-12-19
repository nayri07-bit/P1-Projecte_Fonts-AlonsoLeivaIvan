package p1.t4.daooracle;

import org.junit.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import static org.junit.Assert.*;

public class DAOComponentOracleTest {
  private static Connection conn;

    @BeforeClass
    public static void initConnection() throws Exception {
        String url = "jdbc:oracle:thin:@10.2.104.208/XE";
        String user = "system";
        String pass = "admin";

        conn = DriverManager.getConnection(url, user, pass);
        assertNotNull(conn);

        // Crear la tabla usuarios si no existe
        String createTable = "BEGIN " +
                             "EXECUTE IMMEDIATE 'CREATE TABLE usuarios (" +
                             "id INTEGER PRIMARY KEY, " +
                             "username VARCHAR2(50) NOT NULL, " +
                             "password VARCHAR2(255) NOT NULL)'; " +
                             "EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; " +
                             "END;";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
        }

        // Insertar un usuario con id=1 si no existe
        String mergeUser = "MERGE INTO usuarios u USING dual " +
                           "ON (u.id = 1) " +
                           "WHEN NOT MATCHED THEN " +
                           "INSERT (id, username, password) VALUES (1, 'admin', 'admin')";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(mergeUser);
        }
    }

    @Test
    public void testGetUserById() throws Exception {
        String sql = "SELECT username, password FROM usuarios WHERE id = 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            assertTrue(rs.next());
            String username = rs.getString("username");
            String password = rs.getString("password");
            assertEquals("admin", username);
            assertEquals("admin", password);
        }
    }

    @AfterClass
    public static void closeConnection() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
