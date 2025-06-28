package it.unisa.progetto.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class EliminaSegnalazioneServlet
 */
@WebServlet("/EliminaSegnalazioneServlet")
public class EliminaSegnalazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idSegnalazione = Integer.parseInt(request.getParameter("id_segnalazione"));
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")){
			
			String sql = "DELETE FROM Segnalazioni WHERE id_segnalazione= ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSegnalazione);
            stmt.executeUpdate();
        	response.getWriter().write("Segnalazione cancellata.");
            response.sendRedirect("/ProgettoTSW/Admin/adminSegnalazioni.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore: " + e.getMessage());
		}
	}

}
