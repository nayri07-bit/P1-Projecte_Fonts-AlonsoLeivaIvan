package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOProveidor;
import p1.t4.model.Proveidor;

public class DAOProveidorOracle implements IDAOProveidor {

    private final Connection conn;

    public DAOProveidorOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Proveidor> getAll() {
        List<Proveidor> list = new ArrayList<Proveidor>();
        String sql = "SELECT * FROM proveidor";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Proveidor p = new Proveidor();
                p.setCodi(rs.getInt("pv_codi"));
                p.setCif(rs.getString("pv_cif"));
                p.setRaoSocial(rs.getString("pv_rao_social"));
                p.setLiniaAdrecaFacturacio(rs.getString("pv_lin_adre_fac"));
                p.setPersonaContacte(rs.getString("pv_persona_contacte"));
                p.setTelfContacte(rs.getString("pv_telef_contacte"));
                p.setProvincia(rs.getInt("pv_mu_pr_codi"));
                p.setMunicipi(rs.getInt("pv_mu_num"));
                list.add(p);
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public Proveidor getById(int codi) {
        String sql = "SELECT * FROM proveidor WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Proveidor p = new Proveidor();
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(Proveidor p) throws Exception {
        String sql = "INSERT INTO proveidor (...) VALUES (...)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Proveidor p) throws Exception {
        String sql = "UPDATE proveidor SET ... WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int codi) throws Exception {
        String sql = "DELETE FROM proveidor WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }
    }
}
