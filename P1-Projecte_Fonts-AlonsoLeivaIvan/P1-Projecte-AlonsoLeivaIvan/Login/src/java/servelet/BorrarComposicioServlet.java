package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOComposicioOracle;
import p1.t4.daooracle.OracleConnection;

@WebServlet("/borrarComposicio")
public class BorrarComposicioServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int itPare = Integer.parseInt(request.getParameter("it_pare"));
        int itFill = Integer.parseInt(request.getParameter("it_fill"));

        try (Connection conn = OracleConnection.getConnection()) {
            DAOComposicioOracle dao = new DAOComposicioOracle(conn);
            dao.delete(itPare, itFill);
        } catch(Exception e){
            request.setAttribute("error", "Error borrando composición: " + e.getMessage());
            request.getRequestDispatcher("gestioComposicio?it_pare=" + itPare).forward(request, response);
            return;
        }

        response.sendRedirect("gestioComposicio?it_pare=" + itPare);
    }
}
