package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOItemOracle;
import p1.t4.daooracle.DAOProducteOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Item;
import p1.t4.model.Producte;

@WebServlet("/insertarProducte")
public class InsertarProducteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = OracleConnection.getConnection()) {
            DAOItemOracle daoItem = new DAOItemOracle(conn);
            List<Item> items = daoItem.getAll();
            request.setAttribute("items", items);
            request.getRequestDispatcher("/insertarProducte.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al cargar items para insertar producto", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String itCodiStr = request.getParameter("it_codi");
        if (itCodiStr == null || itCodiStr.isEmpty()) {
            request.setAttribute("error", "Debe seleccionar un item.");
            doGet(request, response);
            return;
        }

        try {
            int itCodi = Integer.parseInt(itCodiStr);

            try (Connection conn = OracleConnection.getConnection()) {
                DAOItemOracle daoItem = new DAOItemOracle(conn);
                DAOProducteOracle daoProd = new DAOProducteOracle(conn);

                Item item = daoItem.getById(itCodi);
                if (item == null) {
                    request.setAttribute("error", "Item no encontrado.");
                    doGet(request, response);
                    return;
                }

                Producte p = new Producte(itCodi, item);
                daoProd.insert(p);

                response.sendRedirect("gestioProductes");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Código inválido.");
            doGet(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al insertar producto: " + e.getMessage());
            doGet(request, response);
        }
    }
}
