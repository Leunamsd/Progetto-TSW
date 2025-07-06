package it.unisa.progetto.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class SegnaòaUtenteServlet
 */
@WebServlet("/SegnalaUtenteServlet")
public class SegnalaUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idUtenteSegnalante = (Integer) request.getSession().getAttribute("id_utente");
        int idUtenteSegnalato = Integer.parseInt(request.getParameter("id_utente_segnalato"));
        String descrizione = request.getParameter("descrizione");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
            String sql = "INSERT INTO Segnalazioni (id_utente_segnalante, id_utente_segnalato, descrizione) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUtenteSegnalante);
            stmt.setInt(2, idUtenteSegnalato);
            stmt.setString(3, descrizione);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtenteSegnalato + "&errore=" + errorMessage);

        }

        response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtenteSegnalato);
    }
}
