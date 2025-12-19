package servelet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOItemOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Item;

@WebServlet("/insertarItem")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class InsertarItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/itemForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = OracleConnection.getConnection()) {

            int codi = Integer.parseInt(request.getParameter("it_codi"));
            String nom = request.getParameter("it_nom");
            String desc = request.getParameter("it_desc");
            int stock = Integer.parseInt(request.getParameter("it_stock"));
            String tipus = request.getParameter("it_tipus");

            Part fotoPart = request.getPart("it_foto");
            byte[] fotoBytes = null;
            if (fotoPart != null && fotoPart.getSize() > 0) {
                try (InputStream is = fotoPart.getInputStream();
                     java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream()) {
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = is.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    fotoBytes = buffer.toByteArray();
                }
            }

            Item item = new Item();
            item.setCodii(codi);
            item.setNom(nom);
            item.setDescr(desc);
            item.setStock(stock);
            item.setEsProducte("p".equals(tipus));
            item.setFoto(fotoBytes);

            DAOItemOracle dao = new DAOItemOracle(conn);
            dao.insert(item);

            response.sendRedirect("gestioItems");

        } catch (Exception e) {
            throw new ServletException("Error insertando item", e);
        }
    }
}
