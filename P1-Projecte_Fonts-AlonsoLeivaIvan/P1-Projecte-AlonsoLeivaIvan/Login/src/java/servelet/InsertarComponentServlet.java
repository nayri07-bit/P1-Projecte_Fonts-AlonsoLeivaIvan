package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.*;
import p1.t4.model.*;

@WebServlet("/insertarComponent")
public class InsertarComponentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cmCodiStr = request.getParameter("cm_codi");
        String cmUmCodiStr = request.getParameter("cm_um_codi");
        String cmFabricant = request.getParameter("cm_codi_fabricant");
        String precioStr = request.getParameter("precioInicial");

        if (cmCodiStr == null || cmUmCodiStr == null || cmFabricant == null || precioStr == null
                || cmCodiStr.isEmpty() || cmUmCodiStr.isEmpty() || cmFabricant.isEmpty() || precioStr.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
            return;
        }

        int cmCodi, cmUmCodi, precioInicial;
        try {
            cmCodi = Integer.parseInt(cmCodiStr);
            cmUmCodi = Integer.parseInt(cmUmCodiStr);
            precioInicial = Integer.parseInt(precioStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Códigos o precio inválidos.");
            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {
            DAOUnitatMesuraOracle daoUM = new DAOUnitatMesuraOracle(conn);
            UnitatMesura um = daoUM.getById(cmUmCodi);
            if (um == null) {
                request.setAttribute("error", "Unidad de medida no encontrada.");
                request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
                return;
            }

            Component comp = new Component(cmCodi, um, cmFabricant, 0);
            DAOComponentOracle daoComp = new DAOComponentOracle(conn);
            daoComp.insert(comp, precioInicial);

            response.sendRedirect(request.getContextPath() + "/components");

        } catch (Exception e) {
            request.setAttribute("error", "Error al guardar componente: " + e.getMessage());
            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
        }
    }
}
