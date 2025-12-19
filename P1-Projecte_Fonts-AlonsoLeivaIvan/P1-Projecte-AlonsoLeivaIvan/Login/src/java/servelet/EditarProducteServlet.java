package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.*;
import p1.t4.model.*;

@WebServlet("/editarProducte")
public class EditarProducteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String prCodiStr = request.getParameter("pr_codi");
        if (prCodiStr == null || prCodiStr.isEmpty()) {
            response.sendRedirect("gestioProductes");
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {

            int prCodi = Integer.parseInt(prCodiStr);
            DAOProducteOracle daoProd = new DAOProducteOracle(conn);
            DAOItemOracle daoItem = new DAOItemOracle(conn);

            Producte p = daoProd.getById(prCodi);
            List<Item> items = daoItem.getAll();

            request.setAttribute("producte", p);
            request.setAttribute("items", items);
            request.getRequestDispatcher("/editarProducte.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error al cargar producto para editar", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String prCodiStr = request.getParameter("pr_codi");
        String itCodiStr = request.getParameter("it_codi");

        if (prCodiStr == null || itCodiStr == null || prCodiStr.isEmpty() || itCodiStr.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            doGet(request, response);
            return;
        }

        try (Connection conn = OracleConnection.getConnection()) {

            int prCodi = Integer.parseInt(prCodiStr);
            int itCodi = Integer.parseInt(itCodiStr);

            DAOItemOracle daoItem = new DAOItemOracle(conn);
            DAOProducteOracle daoProd = new DAOProducteOracle(conn);

            Item item = daoItem.getById(itCodi);
            if (item == null) {
                request.setAttribute("error", "Item no encontrado.");
                doGet(request, response);
                return;
            }

            Producte p = new Producte(prCodi, item);
            daoProd.update(p);

            response.sendRedirect("gestioProductes");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Código inválido.");
            doGet(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error al actualizar producto: " + e.getMessage());
            doGet(request, response);
        }
    }
}
