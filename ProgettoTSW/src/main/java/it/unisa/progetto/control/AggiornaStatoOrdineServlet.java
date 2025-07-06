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
 * Servlet implementation class AggiornaStatoOrdineServlet
 */
@WebServlet("/AggiornaStatoOrdineServlet")
public class AggiornaStatoOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idOrdine = Integer.parseInt(request.getParameter("id_ordine"));
        String nuovoStato = request.getParameter("stato");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
            String sql = "UPDATE Vendite SET stato_vendita = ? WHERE id_vendita = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nuovoStato);
                stmt.setInt(2, idOrdine);
                stmt.executeUpdate();
            }

            response.sendRedirect("/ProgettoTSW/User/ordini.jsp?id=" + idOrdine);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/User/ordini.jsp?id=" + idOrdine + "&errore=" + errorMessage);
        }
    }
}
