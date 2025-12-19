package p1.t4.daooracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;

public class OracleConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;

    static {
        try (InputStream is = OracleConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (is == null) {
                throw new RuntimeException("No se encontró db.properties en el classpath");
            }

            Properties props = new Properties();
            props.load(is);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
            DRIVER = props.getProperty("db.driver");

            Class.forName(DRIVER);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar la configuración de la base de datos", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
