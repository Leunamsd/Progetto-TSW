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
import java.sql.SQLException;

/**
 * Servlet implementation class EliminaProfiloServlet
 */
@WebServlet("/EliminaProfiloServlet")
public class EliminaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
        String ruolo = (String) session.getAttribute("ruolo");
        int sessionIdUtente = (Integer) request.getSession().getAttribute("id_utente");

        if ("amministratore".equals(ruolo) || sessionIdUtente == idUtente) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Utenti WHERE id_utente = ?")) {
                stmt.setInt(1, idUtente);
                stmt.executeUpdate();
                if ("amministratore".equals(ruolo)) {
                    response.sendRedirect("/ProgettoTSW/Admin/adminUtenti.jsp");
                } else {
                    session.invalidate();
                    response.sendRedirect("/ProgettoTSW/Common/index.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Errore SQL: " + e.getMessage());
                response.sendRedirect("/ProgettoTSW/Common/errore.jsp");
            }
        } else {
            response.sendRedirect("/ProgettoTSW/Common/accessoNegato.jsp");
        }
    }
}

