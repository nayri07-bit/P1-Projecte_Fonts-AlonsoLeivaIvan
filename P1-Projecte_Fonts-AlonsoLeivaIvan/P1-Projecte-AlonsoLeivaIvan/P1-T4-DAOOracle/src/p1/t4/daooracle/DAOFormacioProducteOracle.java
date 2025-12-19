package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOFormacioProducte;
import p1.t4.model.FormacioProducte;

public class DAOFormacioProducteOracle implements IDAOFormacioProducte {

    private final Connection conn;

    public DAOFormacioProducteOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<FormacioProducte> getAll() {
        List<FormacioProducte> list = new ArrayList<>();
        String sql = "SELECT * FROM formacio_producte";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FormacioProducte fp = new FormacioProducte();
                list.add(fp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public FormacioProducte getById(int codi) {
        String sql = "SELECT * FROM formacio_producte WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                FormacioProducte fp = new FormacioProducte();
                return fp;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(FormacioProducte fp) {
        String sql = "INSERT INTO formacio_producte (...) VALUES (...)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(FormacioProducte fp) {
        String sql = "UPDATE formacio_producte SET ... WHERE codi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int codi) {
        String sql = "DELETE FROM formacio_producte WHERE codi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
