package servelet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import p1.t4.daooracle.DAOItemOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Item;

@WebServlet("/verFotoItem")
public class VerFotoItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String itCodiStr = request.getParameter("it_codi");
        if (itCodiStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int itCodi = Integer.parseInt(itCodiStr);

        try (Connection conn = OracleConnection.getConnection()) {

            DAOItemOracle dao = new DAOItemOracle(conn);
            Item it = dao.getById(itCodi);

            if (it == null || it.getFoto() == null || it.getFoto().length == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType("image/jpeg");
            response.setContentLength(it.getFoto().length);

            try (OutputStream out = response.getOutputStream()) {
                out.write(it.getFoto());
                out.flush();
            }

        } catch (Exception e) {
            throw new ServletException("Error mostrando foto del item", e);
        }
    }
}
