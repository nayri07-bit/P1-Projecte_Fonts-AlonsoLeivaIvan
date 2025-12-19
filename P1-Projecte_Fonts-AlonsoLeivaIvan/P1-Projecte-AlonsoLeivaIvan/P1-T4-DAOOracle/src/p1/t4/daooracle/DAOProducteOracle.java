package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOProducte;
import p1.t4.model.Item;
import p1.t4.model.Producte;

public class DAOProducteOracle implements IDAOProducte {

    private final Connection conn;

    public DAOProducteOracle(Connection conn) {
        this.conn = conn;
    }

    public List<Producte> getAll() {
        List<Producte> list = new ArrayList<>();
        String sql = "SELECT p.pr_codi, i.it_codi, i.it_nom " +
                     "FROM producte p " +
                     "JOIN item i ON p.pr_codi = i.it_codi";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int prCodi = rs.getInt("pr_codi");
                int itCodi = rs.getInt("it_codi");
                String itNom = rs.getString("it_nom");

                Item item = new Item();
                item.setCodii(itCodi);
                item.setNom(itNom);

                Producte p = new Producte(prCodi, item);
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public Producte getById(int prCodi) {
        String sql = "SELECT pr_codi FROM producte WHERE pr_codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prCodi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Producte p = new Producte();
                p.setCodip(rs.getInt("pr_codi"));
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void insert(Producte p) {
        String sql = "INSERT INTO producte (pr_codi) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getCodip());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Producte p) throws Exception {
        String sql = "UPDATE producte SET ... WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int codi) throws Exception {
        String sql = "DELETE FROM producte WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }
    }
}
