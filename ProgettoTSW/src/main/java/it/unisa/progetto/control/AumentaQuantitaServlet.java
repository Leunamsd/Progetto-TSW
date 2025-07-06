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
 * Servlet implementation class AumentaQuantitaServlet
 */
@WebServlet("/AumentaQuantitaServlet")
public class AumentaQuantitaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idInserzione = Integer.parseInt(request.getParameter("id_inserzione"));
        int quantitaDaAggiungere = Integer.parseInt(request.getParameter("quantita_da_aggiungere"));
        String origine = request.getParameter("origine");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
            String sql = "UPDATE Inserzioni SET quantita = quantita + ? WHERE id_inserzione = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, quantitaDaAggiungere);
                stmt.setInt(2, idInserzione);
                stmt.executeUpdate();
            }
            response.sendRedirect("/ProgettoTSW/Common/inserzione.jsp?id=" + idInserzione);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/Common/inserzione.jsp?id=" + idInserzione + "&errore=" + errorMessage);
        }
    }
}

