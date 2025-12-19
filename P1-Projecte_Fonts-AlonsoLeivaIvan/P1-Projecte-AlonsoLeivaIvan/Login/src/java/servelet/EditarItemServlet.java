package servelet;

import java.io.ByteArrayOutputStream;
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

@WebServlet("/editarItem")
@MultipartConfig(maxFileSize = 1024*1024*5)
public class EditarItemServlet extends HttpServlet {

    private byte[] inputStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try (Connection conn = OracleConnection.getConnection()) {
            DAOItemOracle daoItem = new DAOItemOracle(conn);

            int itCodi = Integer.parseInt(request.getParameter("it_codi"));
            String nombre = request.getParameter("it_nom");
            String descripcion = request.getParameter("it_desc");
            int stock = Integer.parseInt(request.getParameter("it_stock"));
            char tipo = request.getParameter("it_tipus").charAt(0);

            Part fotoPart = request.getPart("it_foto");
            byte[] fotoBytes;

            if (fotoPart != null && fotoPart.getSize() > 0) {
                try (InputStream is = fotoPart.getInputStream()) {
                    fotoBytes = inputStreamToByteArray(is);
                }
            } else {

                Item existingItem = daoItem.getById(itCodi);
                if (existingItem == null) {
                    throw new ServletException("Item no encontrado con código: " + itCodi);
                }
                fotoBytes = existingItem.getFoto();
            }

            Item it = new Item();
            it.setCodii(itCodi);
            it.setNom(nombre);
            it.setDescr(descripcion);
            it.setStock(stock);
            it.setEsProducte(tipo == 'p');
            it.setFoto(fotoBytes);

            daoItem.update(it);

            response.sendRedirect("gestioItems");

        } catch (NumberFormatException e) {
            throw new ServletException("Código o stock inválidos", e);
        } catch (Exception e) {
            throw new ServletException("Error actualizando item", e);
        }
    }
}
