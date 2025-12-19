package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.*;
import p1.t4.model.*;

@WebServlet("/cargarComponent")
public class CargarComponentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = OracleConnection.getConnection()) {

            DAOUnitatMesuraOracle daoUM = new DAOUnitatMesuraOracle(conn);
            DAOItemOracle daoItem = new DAOItemOracle(conn);
            DAOComponentOracle daoComp = new DAOComponentOracle(conn);

            List<UnitatMesura> unitats = daoUM.getAll();
            List<Item> items = daoItem.getAll().stream()
                                      .filter(it -> !it.isEsProducte())
                                      .collect(Collectors.toList());
            List<String> fabricants = daoComp.getDistinctFabricants();

            if(unitats == null) unitats = new java.util.ArrayList<>();
            if(items == null) items = new java.util.ArrayList<>();
            if(fabricants == null) fabricants = new java.util.ArrayList<>();

            request.setAttribute("unitats", unitats);
            request.setAttribute("items", items);
            request.setAttribute("fabricants", fabricants);

            String cmCodiStr = request.getParameter("cm_codi");

            if(cmCodiStr != null && !cmCodiStr.isEmpty()) {
                int cmCodi = Integer.parseInt(cmCodiStr);
                Component comp = daoComp.getById(cmCodi);  
                if(comp == null) {
                    request.setAttribute("error", "No existe el componente");
                } else {
                    request.setAttribute("component", comp);
                }
                request.setAttribute("accion", "editar");
            } else {
                request.setAttribute("accion", "insertar");
            }

            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error cargando componente: " + e.getMessage());
            request.getRequestDispatcher("/componentForm.jsp").forward(request, response);
        }
    }
}
