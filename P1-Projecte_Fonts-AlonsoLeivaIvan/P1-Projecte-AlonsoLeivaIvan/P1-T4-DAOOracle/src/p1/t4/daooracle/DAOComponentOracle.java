package p1.t4.daooracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import p1.t4.idao.IDAOComponent;
import p1.t4.model.Component;
import p1.t4.model.UnitatMesura;

public class DAOComponentOracle implements IDAOComponent {

    private final Connection conn;

    public DAOComponentOracle(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Component> getAll() {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT c.cm_codi, c.cm_codi_fabricant, c.cm_preu_mig, c.cm_um_codi, u.um_nom " +
                     "FROM component c LEFT JOIN unitat_mesura u ON c.cm_um_codi = u.um_codi";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UnitatMesura um = new UnitatMesura(rs.getInt("cm_um_codi"), rs.getString("um_nom"));
                components.add(new Component(
                        rs.getInt("cm_codi"),
                        um,
                        rs.getString("cm_codi_fabricant"),
                        rs.getDouble("cm_preu_mig")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return components;
    }

    @Override
    public Component getById(int codic) {
        String sql = "SELECT c.cm_codi, c.cm_codi_fabricant, c.cm_preu_mig, c.cm_um_codi, u.um_nom " +
                     "FROM component c JOIN unitat_mesura u ON c.cm_um_codi = u.um_codi " +
                     "WHERE c.cm_codi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codic);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UnitatMesura um = new UnitatMesura(rs.getInt("cm_um_codi"), rs.getString("um_nom"));
                    return new Component(
                            rs.getInt("cm_codi"),
                            um,
                            rs.getString("cm_codi_fabricant"),
                            rs.getDouble("cm_preu_mig")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(Component component, int precioInicial) throws SQLException {
        String sqlInsertComp = "INSERT INTO component(cm_codi, cm_um_codi, cm_codi_fabricant, cm_preu_mig) VALUES (?, ?, ?, 0)";
        try (PreparedStatement psComp = conn.prepareStatement(sqlInsertComp)) {
            psComp.setInt(1, component.getCodic());
            psComp.setInt(2, component.getUnitatMesura().getCodiu());
            psComp.setString(3, component.getCodiFabricant());
            psComp.executeUpdate();
        }

        String sqlInsertProvComp = "INSERT INTO prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) VALUES (?, ?, ?)";
        try (PreparedStatement psProvComp = conn.prepareStatement(sqlInsertProvComp);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT pv_codi FROM proveidor")) {

            while (rs.next()) {
                int pvCodi = rs.getInt("pv_codi");
                psProvComp.setInt(1, component.getCodic());
                psProvComp.setInt(2, pvCodi);
                psProvComp.setInt(3, precioInicial);
                psProvComp.addBatch();
            }
            psProvComp.executeBatch();
        }
    }

    @Override
    public void update(Component component) {
        String sql = "UPDATE component SET cm_um_codi=?, cm_codi_fabricant=? WHERE cm_codi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, component.getUnitatMesura().getCodiu());
            ps.setString(2, component.getCodiFabricant());
            ps.setInt(3, component.getCodic());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int codic) {
        String sql = "DELETE FROM component WHERE cm_codi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codic);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getPreuMig(int codic) {
        String sql = "SELECT cm_preu_mig FROM component WHERE cm_codi=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codic);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("cm_preu_mig");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void asignarPrecioProveedor(int cmCodi, int pvCodi, int precio) {
        String checkSql = "SELECT COUNT(*) FROM prov_comp WHERE pc_cm_it_codi = ? AND pc_pv_codi = ?";
        String insertSql = "INSERT INTO prov_comp(pc_cm_it_codi, pc_pv_codi, pc_preu) VALUES (?, ?, ?)";
        String updateSql = "UPDATE prov_comp SET pc_preu = ? WHERE pc_cm_it_codi = ? AND pc_pv_codi = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, cmCodi);
            checkStmt.setInt(2, pvCodi);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, precio);
                        updateStmt.setInt(2, cmCodi);
                        updateStmt.setInt(3, pvCodi);
                        updateStmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, cmCodi);
                        insertStmt.setInt(2, pvCodi);
                        insertStmt.setInt(3, precio);
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al asignar precio al proveedor " + pvCodi + " para el componente " + cmCodi, e);
        }
    }

    @Override
    public List<Component> getAllOrdenat(String ordre) {
        List<Component> components = getAll();
        components.sort((a, b) -> {
            if ("id".equalsIgnoreCase(ordre)) return Integer.compare(a.getCodic(), b.getCodic());
            if ("fabricant".equalsIgnoreCase(ordre)) return a.getCodiFabricant().compareToIgnoreCase(b.getCodiFabricant());
            if ("preu".equalsIgnoreCase(ordre)) return Double.compare(a.getPreuMig(), b.getPreuMig());
            return 0;
        });
        return components;
    }

    @Override
    public List<Component> getByFabricant(String codiFabricant) {
        List<Component> all = getAll();
        List<Component> filtrados = new ArrayList<>();
        for (Component c : all) {
            if (c.getCodiFabricant().equalsIgnoreCase(codiFabricant)) filtrados.add(c);
        }
        return filtrados;
    }

    @Override
    public List<String> getDistinctFabricants() {
        List<String> fabricants = new ArrayList<>();
        String sql = "SELECT DISTINCT cm_codi_fabricant FROM component";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                fabricants.add(rs.getString("cm_codi_fabricant"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fabricants;
    }
}
