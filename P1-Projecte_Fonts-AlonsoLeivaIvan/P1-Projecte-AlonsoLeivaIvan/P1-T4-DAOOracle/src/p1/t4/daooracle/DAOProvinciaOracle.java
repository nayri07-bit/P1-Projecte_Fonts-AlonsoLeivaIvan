package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOProvincia;
import p1.t4.model.Provincia;

public class DAOProvinciaOracle implements IDAOProvincia {

    private final Connection conn;

    public DAOProvinciaOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Provincia> getAll() {
        List<Provincia> list = new ArrayList<>();
        String sql = "SELECT * FROM provincia";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Provincia p = new Provincia();
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Provincia getById(int codi) {
        String sql = "SELECT * FROM provincia WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Provincia p = new Provincia();
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
