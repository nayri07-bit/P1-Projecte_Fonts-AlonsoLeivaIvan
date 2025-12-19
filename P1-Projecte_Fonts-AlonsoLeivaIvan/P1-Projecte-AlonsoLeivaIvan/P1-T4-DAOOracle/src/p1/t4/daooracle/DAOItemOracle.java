package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import p1.t4.idao.IDAOItem;
import p1.t4.model.Item;
import java.util.List;
public class DAOItemOracle implements IDAOItem {

    private final Connection conn;

    public DAOItemOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto FROM item";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Item getById(int codi) {
        String sql = "SELECT it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto FROM item WHERE it_codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(Item item) {
        String sql = "INSERT INTO item (it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getCodii());
            ps.setString(2, item.isEsProducte() ? "p" : "c");
            ps.setString(3, item.getNom());
            ps.setString(4, item.getDescr());
            ps.setInt(5, item.getStock());
            if (item.getFoto() != null && item.getFoto().length > 0) {
                ps.setBytes(6, item.getFoto());
            } else {
                ps.setNull(6, Types.BLOB);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Item item) {
        String sql = "UPDATE item SET it_tipus = ?, it_nom = ?, it_desc = ?, it_stock = ?, it_foto = ? WHERE it_codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.isEsProducte() ? "p" : "c");
            ps.setString(2, item.getNom());
            ps.setString(3, item.getDescr());
            ps.setInt(4, item.getStock());
            if (item.getFoto() != null && item.getFoto().length > 0) {
                ps.setBytes(5, item.getFoto());
            } else {
                ps.setNull(5, Types.BLOB);
            }
            ps.setInt(6, item.getCodii());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int codi) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM prod_item WHERE pi_pr_codi = ?")) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT pi_it_codi FROM prod_item WHERE pi_pr_codi = ?")) {
            ps.setInt(1, codi);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int hijo = rs.getInt("pi_it_codi");
                    delete(hijo);
                }
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM prod_item WHERE pi_it_codi = ?")) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM producte WHERE pr_codi = ?")) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM prov_comp WHERE pc_cm_it_codi = ?")) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM component WHERE cm_codi = ?")) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM item WHERE it_codi = ?")) {
            ps.setInt(1, codi);
            ps.executeUpdate();
        }
    } 

    public List<Item> getItemsFiltrados(String buscarCodi, String buscarNom, String orden, String ordenDir) throws SQLException {
        List<Item> items = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto FROM item WHERE 1=1 ");

        if (buscarCodi != null && !buscarCodi.trim().isEmpty()) {
            sql.append(" AND TO_CHAR(it_codi) LIKE ? ");
        }

        if (buscarNom != null && !buscarNom.trim().isEmpty()) {
            sql.append(" AND LOWER(it_nom) LIKE ? ");
        }

        List<String> columnasValidas = List.of("it_codi", "it_nom", "it_stock");
        if (orden != null && columnasValidas.contains(orden)) {
            sql.append(" ORDER BY ").append(orden);
            if ("desc".equalsIgnoreCase(ordenDir)) {
                sql.append(" DESC");
            } else {
                sql.append(" ASC");
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;

            if (buscarCodi != null && !buscarCodi.trim().isEmpty()) {
                ps.setString(index++, "%" + buscarCodi.trim() + "%");
            }

            if (buscarNom != null && !buscarNom.trim().isEmpty()) {
                ps.setString(index++, "%" + buscarNom.trim().toLowerCase() + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item it = new Item();
                    it.setCodii(rs.getInt("it_codi"));
                    it.setEsProducte(rs.getInt("it_tipus") == 1);
                    it.setNom(rs.getString("it_nom"));
                    it.setDescr(rs.getString("it_desc"));
                    it.setStock(rs.getInt("it_stock"));
                    it.setFoto(rs.getBytes("it_foto"));
                    items.add(it);
                }
            }
        }

        return items;
    }


    private Item mapRow(ResultSet rs) throws SQLException {
        Item it = new Item();
        it.setCodii(rs.getInt("it_codi"));
        it.setNom(rs.getString("it_nom"));
        it.setDescr(rs.getString("it_desc"));
        it.setStock(rs.getInt("it_stock"));
        it.setEsProducte("p".equals(rs.getString("it_tipus")));

        Blob blob = rs.getBlob("it_foto");
        if (blob != null && blob.length() > 0) {
            it.setFoto(blob.getBytes(1, (int) blob.length()));
        } else {
            it.setFoto(null);
        }

        return it;
    }
}
