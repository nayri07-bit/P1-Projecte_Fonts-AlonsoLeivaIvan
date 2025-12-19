package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import p1.t4.daooracle.DAOComposicioOracle;
import p1.t4.daooracle.OracleConnection;

@WebServlet("/insertarComposicio")
public class InsertarComposicioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int itPare = Integer.parseInt(request.getParameter("it_pare"));
            int itFill = Integer.parseInt(request.getParameter("it_fill"));
            int quantitat = Integer.parseInt(request.getParameter("quantitat"));

            try (Connection conn = OracleConnection.getConnection()) {
                DAOComposicioOracle dao = new DAOComposicioOracle(conn);
                dao.insert(itPare, itFill, quantitat);
            }

            response.sendRedirect("gestioComposicio?it_pare=" + itPare);

        } catch (NumberFormatException e) {
            throw new ServletException("Parámetros inválidos", e);
        } catch (Exception e) {
            throw new ServletException("Error insertando item hijo", e);
        }
    }
}
