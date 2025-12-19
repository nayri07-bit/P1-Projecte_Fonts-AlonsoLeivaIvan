package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.*;
import p1.t4.model.Proveidor;

@WebServlet("/asignarPrecios")
public class AsignarPreciosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cmCodiStr = request.getParameter("cm_codi");
        if(cmCodiStr == null || cmCodiStr.isEmpty()){
            request.setAttribute("error", "No se ha indicado un componente");
            request.getRequestDispatcher("components").forward(request, response);
            return;
        }

        int cmCodi = Integer.parseInt(cmCodiStr);

        try (Connection conn = OracleConnection.getConnection()) {
            DAOProveidorOracle daoProv = new DAOProveidorOracle(conn);
            List<Proveidor> proveidors = daoProv.getAll();
            request.setAttribute("cm_codi", cmCodi);
            request.setAttribute("proveidors", proveidors);
            request.getRequestDispatcher("/asignarPrecios.jsp").forward(request, response);
        } catch(Exception e){
            request.setAttribute("error", "Error cargando proveedores: " + e.getMessage());
            request.getRequestDispatcher("components").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cmCodiStr = request.getParameter("cm_codi");
        if(cmCodiStr == null || cmCodiStr.isEmpty()){
            request.setAttribute("error", "No se ha indicado un componente");
            request.getRequestDispatcher("components").forward(request, response);
            return;
        }

        int cmCodi = Integer.parseInt(cmCodiStr);

        try (Connection conn = OracleConnection.getConnection()) {
            DAOComponentOracle daoComp = new DAOComponentOracle(conn);

            request.getParameterMap().forEach((param, values) -> {
                if(param.startsWith("precio_")){
                    try{
                        int pvCodi = Integer.parseInt(param.substring(7));
                        int precio = Integer.parseInt(values[0]);
                        if(precio <= 0) throw new RuntimeException("Precio inválido");
                        daoComp.asignarPrecioProveedor(cmCodi, pvCodi, precio);
                    } catch(Exception e){
                        throw new RuntimeException("Error asignando precio: " + e.getMessage());
                    }
                }
            });

            response.sendRedirect("components");

        } catch(Exception e){
            request.setAttribute("error", "Error guardando precios: " + e.getMessage());
            request.getRequestDispatcher("components").forward(request, response);
        }
    }
}
