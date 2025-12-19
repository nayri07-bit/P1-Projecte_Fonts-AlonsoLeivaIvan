package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOItemOracle;
import p1.t4.daooracle.OracleConnection;

@WebServlet("/borrarItems")
public class BorrarItemsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] codis = request.getParameterValues("it_codi");

        if (codis == null || codis.length == 0) {
            response.sendRedirect("gestioItems?error=Seleccione al menos un item");
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {
            DAOItemOracle dao = new DAOItemOracle(conn);

            for (String codiStr : codis) {
                int codi = Integer.parseInt(codiStr);
                dao.delete(codi);
            }

            response.sendRedirect("gestioItems?success=Items eliminados correctamente");
        } catch (Exception e) {
            response.sendRedirect("gestioItems?error=" + e.getMessage());
        }
    }
}


