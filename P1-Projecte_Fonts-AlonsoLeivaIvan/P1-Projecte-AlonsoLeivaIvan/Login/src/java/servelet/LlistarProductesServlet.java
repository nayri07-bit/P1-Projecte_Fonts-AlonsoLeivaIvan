package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOProducteOracle;
import p1.t4.daooracle.OracleConnection;
import p1.t4.model.Producte;

@WebServlet("/llistarProductes")
public class LlistarProductesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = OracleConnection.getConnection()) {

            DAOProducteOracle daoProd = new DAOProducteOracle(conn);
            List<Producte> productes = daoProd.getAll();
            request.setAttribute("productes", productes);

            request.getRequestDispatcher("/productesList.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al obtener la lista de productos", e);
        }
    }
}
