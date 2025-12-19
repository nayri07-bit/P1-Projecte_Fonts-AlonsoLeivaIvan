package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOMunicipi;
import p1.t4.model.Municipi;

public class DAOMunicipiOracle implements IDAOMunicipi {

    private final Connection conn;

    public DAOMunicipiOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Municipi> getAll() {
        List<Municipi> list = new ArrayList<>();
        String sql = "SELECT * FROM municipi";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Municipi m = new Municipi();
                list.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Municipi getById(int codi) {
        String sql = "SELECT * FROM municipi WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Municipi m = new Municipi();
                return m;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
