package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOComponentOracle;
import p1.t4.daooracle.OracleConnection;

@WebServlet("/borrarComponents")
public class BorrarComponentsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] codigos = request.getParameterValues("cm_codi");
        if(codigos == null || codigos.length == 0){
            request.setAttribute("error", "Seleccione al menos un componente para borrar");
            request.getRequestDispatcher("components").forward(request, response);
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {
            DAOComponentOracle daoComp = new DAOComponentOracle(conn);
            for(String c : codigos){
                int codic = Integer.parseInt(c);
                daoComp.delete(codic);
            }
            response.sendRedirect("components");
        } catch(Exception e){
            request.setAttribute("error", "Error borrando componentes: " + e.getMessage());
            request.getRequestDispatcher("components").forward(request, response);
        }
    }
}
