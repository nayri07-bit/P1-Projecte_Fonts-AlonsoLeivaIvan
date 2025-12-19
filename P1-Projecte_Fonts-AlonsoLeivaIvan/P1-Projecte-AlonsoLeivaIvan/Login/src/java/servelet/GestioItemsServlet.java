package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Item;

@WebServlet("/gestioItems")
public class GestioItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String buscarCodi = request.getParameter("buscarCodi");
        String buscarNom = request.getParameter("buscarNom");
        String orden = request.getParameter("orden");

        List<Item> items = new ArrayList<>();

        try (Connection conn = OracleConnection.getConnection()) {

            StringBuilder sql = new StringBuilder(
                "SELECT it_codi, it_tipus, it_nom, it_desc, it_stock, it_foto FROM item WHERE 1=1"
            );

            if (buscarCodi != null && !buscarCodi.isEmpty()) {
                sql.append(" AND it_codi = ?");
            }

            if (buscarNom != null && !buscarNom.isEmpty()) {
                sql.append(" AND LOWER(it_nom) LIKE ?");
            }

            List<String> columnasValidas = Arrays.asList("codigo", "nombre", "stock");
            if (orden != null && columnasValidas.contains(orden)) {
                if ("codigo".equals(orden)) {
                    sql.append(" ORDER BY it_codi");
                } else if ("nombre".equals(orden)) {
                    sql.append(" ORDER BY it_nom");
                } else if ("stock".equals(orden)) {
                    sql.append(" ORDER BY it_stock");
                }
            }

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int idx = 1;

            if (buscarCodi != null && !buscarCodi.isEmpty()) {
                ps.setInt(idx++, Integer.parseInt(buscarCodi));
            }

            if (buscarNom != null && !buscarNom.isEmpty()) {
                ps.setString(idx++, "%" + buscarNom.toLowerCase() + "%");
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Item it = new Item();
                it.setCodii(rs.getInt("it_codi"));
                it.setEsProducte("p".equals(rs.getString("it_tipus")));
                it.setNom(rs.getString("it_nom"));
                it.setDescr(rs.getString("it_desc"));
                it.setStock(rs.getInt("it_stock"));
                it.setFoto(rs.getBytes("it_foto"));
                items.add(it);
            }

            request.setAttribute("items", items);
            request.setAttribute("orden", orden);
            request.getRequestDispatcher("productesList.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error cargando items", e);
        }
    }
}
