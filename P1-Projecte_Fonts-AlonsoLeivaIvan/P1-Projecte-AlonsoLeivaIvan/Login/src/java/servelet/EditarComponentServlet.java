package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOComponentOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Component;
import p1.t4.model.UnitatMesura;

@WebServlet("/editarComponent")
public class EditarComponentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String error = null;

        try {
            int cmCodi = Integer.parseInt(request.getParameter("cm_codi"));
            int umCodi = Integer.parseInt(request.getParameter("cm_um_codi"));
            String fabricant = request.getParameter("cm_codi_fabricant");

            try (Connection conn = OracleConnection.getConnection()) {
                DAOComponentOracle daoComp = new DAOComponentOracle(conn);

                Component c = new Component();
                c.setCodic(cmCodi);
                c.setUnitatMesura(new UnitatMesura(umCodi));
                c.setCodiFabricant(fabricant);

                daoComp.update(c); 
            }

            response.sendRedirect("components");

        } catch (NumberFormatException e) {
            error = "Código o unidad de medida inválidos.";
        } catch (Exception e) {
            error = "Error al guardar componente: " + e.getMessage();
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
        }
    }
}
