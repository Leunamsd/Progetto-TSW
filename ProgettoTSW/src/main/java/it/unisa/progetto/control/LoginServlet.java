package it.unisa.progetto.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username= request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");

            String sql = "SELECT id_utente, username, password, ruolo FROM utenti WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	
            	int id_utenteRS = rs.getInt("id_utente");
                String usernameRS = rs.getString("username");
                String passwordRS = rs.getString("password");
                String ruolo = rs.getString("ruolo");

                if (username.equals(usernameRS) && BCrypt.checkpw(password, passwordRS)) {
                    // Login valido, crea sessione
                    HttpSession session = request.getSession();
                    session.setAttribute("id_utente", id_utenteRS);
                    session.setAttribute("username", usernameRS);
                    session.setAttribute("ruolo", ruolo);
                    
                    response.sendRedirect("Common/index.jsp");
                    
                    return;
                }
            }

            // Se login fallito
            response.sendRedirect("/ProgettoTSW/Common/login.jsp?errore=1");

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/ProgettoTSW/Common/login.jsp?errore=2");
        }
    }
}