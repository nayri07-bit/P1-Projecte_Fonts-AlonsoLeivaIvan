package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.*;
import p1.t4.model.*;

@WebServlet("/guardarComponente")
public class GuardarComponenteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String cmCodiStr = request.getParameter("cm_codi");
        String umCodiStr = request.getParameter("cm_um_codi");
        String cmFabricant = request.getParameter("cm_codi_fabricant");
        String precioStr = request.getParameter("precio");

        if (cmCodiStr == null || umCodiStr == null || cmFabricant == null || precioStr == null
                || cmCodiStr.isEmpty() || umCodiStr.isEmpty() || cmFabricant.isEmpty() || precioStr.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
            return;
        }

        int cmCodi, umCodi, precioInicial;
        try {
            cmCodi = Integer.parseInt(cmCodiStr);
            umCodi = Integer.parseInt(umCodiStr);
            precioInicial = Integer.parseInt(precioStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Códigos o precio inválidos.");
            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {
            DAOUnitatMesuraOracle daoUM = new DAOUnitatMesuraOracle(conn);
            UnitatMesura um = daoUM.getById(umCodi);
            if (um == null) {
                request.setAttribute("error", "Unidad de medida no encontrada.");
                request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
                return;
            }

            Component comp = new Component(cmCodi, um, cmFabricant, 0);
            DAOComponentOracle daoComp = new DAOComponentOracle(conn);

            if ("insertar".equalsIgnoreCase(accion)) {
                daoComp.insert(comp, precioInicial);
            } else if ("editar".equalsIgnoreCase(accion)) {
                daoComp.update(comp);
            }

            response.sendRedirect(request.getContextPath() + "/components");

        } catch (SQLException e) {
            throw new ServletException("Error al guardar componente", e);
        }
    }
}
