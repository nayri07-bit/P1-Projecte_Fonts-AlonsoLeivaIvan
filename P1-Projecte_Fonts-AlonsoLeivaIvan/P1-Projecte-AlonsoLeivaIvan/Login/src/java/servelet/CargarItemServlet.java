package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOItemOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Item;

@WebServlet("/cargarItem")
public class CargarItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String itCodiStr = request.getParameter("it_codi");
        if (itCodiStr == null) {
            response.sendRedirect("gestioItems");
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {
            DAOItemOracle daoItem = new DAOItemOracle(conn);
            int itCodi = Integer.parseInt(itCodiStr);
            Item it = daoItem.getById(itCodi);

            if (it == null) {
                request.setAttribute("error", "Item no encontrado con código " + itCodi);
            }

            request.setAttribute("item", it);
            request.getRequestDispatcher("/itemForm.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error cargando item", e);
        }
    }
}
