package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.model.Composicio;

public class DAOComposicioOracle {

    private final Connection conn;

    public DAOComposicioOracle(Connection conn) {
        this.conn = conn;
    }

    // Obtener todos los hijos de un producto
    public List<Composicio> getByProducte(int itPare) {
        List<Composicio> list = new ArrayList<>();
        String sql = "SELECT pi_it_codi, it_nom, quantitat " +
                     "FROM prod_item pi " +
                     "JOIN item i ON pi.pi_it_codi = i.it_codi " +
                     "WHERE pi_pr_codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itPare);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Composicio c = new Composicio();
                c.setItPare(itPare);
                c.setItFill(rs.getInt("pi_it_codi"));
                c.setNom(rs.getString("it_nom"));
                c.setQuantitat(rs.getInt("quantitat"));
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Insertar un hijo
    public void insert(int itPare, int itFill, int quantitat) {
        String sql = "INSERT INTO prod_item (pi_pr_codi, pi_it_codi, quantitat) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itPare);
            ps.setInt(2, itFill);
            ps.setInt(3, quantitat);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Borrar un hijo
    public void delete(int itPare, int itFill) {
        String sql = "DELETE FROM prod_item WHERE pi_pr_codi = ? AND pi_it_codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itPare);
            ps.setInt(2, itFill);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
