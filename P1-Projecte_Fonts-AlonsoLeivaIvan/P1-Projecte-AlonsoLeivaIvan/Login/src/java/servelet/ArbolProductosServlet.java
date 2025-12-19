package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.*;
import p1.t4.model.*;

@WebServlet("/arbolProductos")
public class ArbolProductosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String itCodiStr = request.getParameter("it_codi");
        if(itCodiStr == null || itCodiStr.isEmpty()){
            request.setAttribute("error", "No se ha indicado un item");
            request.getRequestDispatcher("gestioItems").forward(request, response);
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {

            DAOItemOracle daoItem = new DAOItemOracle(conn);
            DAOComposicioOracle daoComp = new DAOComposicioOracle(conn);

            int itCodi = Integer.parseInt(itCodiStr);
            Item item = daoItem.getById(itCodi);
            if(item == null){
                request.setAttribute("error", "Item no encontrado");
                request.getRequestDispatcher("gestioItems").forward(request, response);
                return;
            }

            List<Composicio> hijos = daoComp.getByProducte(itCodi);
            request.setAttribute("item", item);
            request.setAttribute("hijos", hijos);

            request.getRequestDispatcher("/arbolProductos.jsp").forward(request, response);

        } catch(Exception e){
            request.setAttribute("error", "Error generando árbol: " + e.getMessage());
            request.getRequestDispatcher("gestioItems").forward(request, response);
        }
    }
}
