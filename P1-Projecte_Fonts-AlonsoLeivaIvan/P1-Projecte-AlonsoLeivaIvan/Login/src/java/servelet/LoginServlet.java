package servelet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import p1.t4.daooracle.DAOUser;
import p1.t4.model.User;
import p1.t4.daooracle.OracleConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = OracleConnection.getConnection()) {
            DAOUser dao = new DAOUser(conn);
            User user = dao.trobarUser(username);

            if(user != null && DAOUser.checkPassword(password, user.getPassword())) {
                request.getSession().setAttribute("usuari", user);
                response.sendRedirect("home.jsp"); // Canvia a la pàgina inicial de l'aplicació
            } else {
                request.setAttribute("error", "Usuari o contrasenya incorrectes");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch(Exception e) {
            throw new ServletException("Error en login", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
