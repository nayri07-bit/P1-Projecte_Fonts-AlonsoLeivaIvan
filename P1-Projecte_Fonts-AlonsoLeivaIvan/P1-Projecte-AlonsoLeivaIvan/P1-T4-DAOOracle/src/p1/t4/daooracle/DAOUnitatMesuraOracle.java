package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOUnitatMesura;
import p1.t4.model.UnitatMesura;

public class DAOUnitatMesuraOracle implements IDAOUnitatMesura {

    private final Connection conn;

    public DAOUnitatMesuraOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<UnitatMesura> getAll() {
        List<UnitatMesura> list = new ArrayList<>();
        String sql = "SELECT * FROM unitat_mesura";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UnitatMesura u = new UnitatMesura(rs.getInt("UM_CODI"), rs.getString("UM_NOM"));
                list.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public UnitatMesura getById(int codi) {
        String sql = "SELECT * FROM unitat_mesura WHERE UM_CODI = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UnitatMesura(rs.getInt("UM_CODI"), rs.getString("UM_NOM"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
