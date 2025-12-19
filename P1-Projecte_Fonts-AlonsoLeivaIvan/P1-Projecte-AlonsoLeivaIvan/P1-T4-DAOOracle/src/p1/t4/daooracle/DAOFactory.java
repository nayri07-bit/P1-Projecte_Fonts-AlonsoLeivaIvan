package p1.t4.daooracle;
import p1.t4.idao.IDAOComponent;
import java.sql.Connection;
import java.sql.SQLException;
import p1.t4.idao.*;

public class DAOFactory {

    private static Connection conn;

    static {
        try {
            conn = OracleConnection.getConnection(); 
            System.out.println("DAOFactory: conexión obtenida = " + conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DAOFactory() {}

    public static IDAOComponent getDAOComponent() {
        return new DAOComponentOracle(conn);
    }

    public static IDAOProducte getDAOProducte() {
        return new DAOProducteOracle(conn);
    }

    public static IDAOProveidor getDAOProveidor() {
        return new DAOProveidorOracle(conn);
    }

    public static IDAOMunicipi getDAOMunicipi() {
        return new DAOMunicipiOracle(conn);
    }

    public static IDAOProvincia getDAOProvincia() {
        return new DAOProvinciaOracle(conn);
    }

    public static IDAOUnitatMesura getDAOUnitatMesura() {
        return new DAOUnitatMesuraOracle(conn);
    }

    public static IDAOCompres getDAOCompres() {
        return new DAOCompresOracle(conn);
    }

    public static IDAOItem getDAOItem() {
        return new DAOItemOracle(conn);
    }

    public static IDAOFormacioProducte getDAOFormacioProducte() {
        return new DAOFormacioProducteOracle(conn);
    }
    
    public static IDAOUser getDAOUsuari() {
        return new DAOUser(conn);
    }
}
