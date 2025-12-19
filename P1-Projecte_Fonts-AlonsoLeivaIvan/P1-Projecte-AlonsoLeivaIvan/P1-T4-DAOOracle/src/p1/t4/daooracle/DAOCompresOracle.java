package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOCompres;
import p1.t4.model.Compres;

public class DAOCompresOracle implements IDAOCompres {

    private final Connection conn;

    public DAOCompresOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Compres> getAll() {
        List<Compres> compresList = new ArrayList<>();
        String sql = "SELECT * FROM compres";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Compres c = new Compres();
                compresList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compresList;
    }

    @Override
    public void insert(Compres c) {
        String sql = "INSERT INTO compres (...) VALUES (...)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Compres c) {
        String sql = "UPDATE compres SET ... WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM compres WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Compres getById(int codi) {
        String sql = "SELECT * FROM compres WHERE codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Compres c = new Compres();
                return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
