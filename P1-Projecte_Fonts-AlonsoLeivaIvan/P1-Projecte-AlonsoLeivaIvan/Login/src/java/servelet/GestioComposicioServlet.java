package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.*;
import p1.t4.model.Producte;

@WebServlet("/gestioComposicio")
public class GestioComposicioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = OracleConnection.getConnection()) {

            int itPare = Integer.parseInt(request.getParameter("it_pare"));

            DAOComposicioOracle daoComp = new DAOComposicioOracle(conn);
            DAOItemOracle daoItem = new DAOItemOracle(conn);

            request.setAttribute("itPare", itPare);
            request.setAttribute("composicio", daoComp.getByProducte(itPare));
            request.setAttribute("itemsDisponibles", daoItem.getAll());

            request.getRequestDispatcher("/composicio.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error cargando composición", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        try (Connection conn = OracleConnection.getConnection()) {

            int itPare = Integer.parseInt(request.getParameter("it_pare"));
            int itCodi = Integer.parseInt(request.getParameter("it_codi"));
            int quantitat = Integer.parseInt(request.getParameter("quantitat"));

            DAOProducteOracle daoProd = new DAOProducteOracle(conn);
            if (daoProd.getById(itPare) == null) {
                Producte prodPare = new Producte();
                prodPare.setCodip(itPare);
                daoProd.insert(prodPare);
            }

            DAOComposicioOracle daoComp = new DAOComposicioOracle(conn);

            try {
                daoComp.insert(itPare, itCodi, quantitat);
                response.sendRedirect("gestioComposicio?it_pare=" + itPare);
            } catch (RuntimeException ex) {
                Throwable cause = ex.getCause();
                if (cause != null && cause instanceof java.sql.SQLIntegrityConstraintViolationException) {
                    request.setAttribute("error", "El componente ya existe en la composición.");
                    request.setAttribute("itPare", itPare);
                    request.setAttribute("composicio", daoComp.getByProducte(itPare));
                    DAOItemOracle daoItem = new DAOItemOracle(conn);
                    request.setAttribute("itemsDisponibles", daoItem.getAll());
                    request.getRequestDispatcher("/composicio.jsp").forward(request, response);
                } else {
                    throw ex;
                }
            }

        } catch (Exception e) {
            throw new ServletException("Error insertando item en composición", e);
        }
    }

}
