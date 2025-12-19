package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOComponentOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Component;

@WebServlet("/components")
public class ComponentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String buscarIdStr = request.getParameter("buscarId");
        String ordre = request.getParameter("ordre");

        Integer buscarId = null;
        if (buscarIdStr != null && !buscarIdStr.trim().isEmpty()) {
            try {
                buscarId = Integer.parseInt(buscarIdStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "El ID debe ser numérico");
            }
        }

        try (Connection conn = OracleConnection.getConnection()) {

            DAOComponentOracle dao = new DAOComponentOracle(conn);
            List<Component> components = new ArrayList<>();

            if (buscarId != null) {
                Component c = dao.getById(buscarId);
                if (c != null) components.add(c);
                else request.setAttribute("error", "No existe componente con ID " + buscarId);
            } else {
                components = dao.getAllOrdenat(ordre); 
            }

            request.setAttribute("components", components);
            request.setAttribute("ordre", ordre != null ? ordre : "");
            request.setAttribute("buscarId", buscarIdStr != null ? buscarIdStr : "");

            request.getRequestDispatcher("/components.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error cargando componentes: " + e.getMessage());
            request.getRequestDispatcher("/components.jsp").forward(request, response);
        }
    }
}
