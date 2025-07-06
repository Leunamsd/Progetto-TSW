package it.unisa.progetto.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class AggiornaSegnalazioneServlet
 */
@WebServlet("/AggiornaSegnalazioneServlet")
public class AggiornaSegnalazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSegnalazione = Integer.parseInt(request.getParameter("id_segnalazione"));
        String stato = request.getParameter("stato");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
            String sql = "UPDATE Segnalazioni SET stato = ? WHERE id_segnalazione = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, stato);
            stmt.setInt(2, idSegnalazione);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/Admin/adminSegnalazioni.jsp=" + errorMessage);
        }

        response.sendRedirect("/ProgettoTSW/Admin/adminSegnalazioni.jsp");
    }
}

