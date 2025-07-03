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
import java.sql.SQLException;

/**
 * Servlet implementation class EliminaInserzioneServlet
 */
@WebServlet("/EliminaInserzioneServlet")
public class EliminaInserzioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
        String origine = request.getParameter("origine");
        Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
		int idInserzione = Integer.parseInt(request.getParameter("id_inserzione"));
        HttpSession session = request.getSession();
        String ruolo = (String) session.getAttribute("ruolo");

        if ("amministratore".equals(ruolo) || idUtente != null) {
        		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")){
	        		String sql = "DELETE FROM Inserzioni WHERE id_inserzione = ? AND id_utente_inserzionista = ?";
	            	PreparedStatement stmt = conn.prepareStatement(sql);
		            stmt.setInt(1, idInserzione);
		            stmt.setInt(2, idUtente);
	                stmt.executeUpdate();

                // Redirect in base all'origine
                if ("admin".equals(origine)) {
                	response.getWriter().write("Inserzione cancellata.");
                    response.sendRedirect("/ProgettoTSW/Admin/adminInserzioni.jsp");
                } else {
                    response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtente);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Errore: " + e.getMessage());
                response.sendRedirect("/ProgettoTSW/Common/errore.jsp");
            }
        } else {
            response.sendRedirect("/ProgettoTSW/Common/accessoNegato.jsp");
        }
    }
}

