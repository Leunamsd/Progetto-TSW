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

@WebServlet("/GestisciCarrelloServlet")
public class GestisciCarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
        String azione = request.getParameter("azione");  // "rimuovi" o "svuota"

        if (idUtente == null || azione == null) {
            response.sendRedirect("errore.jsp");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {

            if ("rimuovi".equals(azione)) {
                int idInserzione = Integer.parseInt(request.getParameter("id_inserzione"));
                String sql = "DELETE FROM Carrello WHERE id_utente = ? AND id_carta = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, idUtente);
                    stmt.setInt(2, idInserzione);
                    stmt.executeUpdate();
                }

            } else if ("svuota".equals(azione)) {
                String sql = "DELETE FROM Carrello WHERE id_utente = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, idUtente);
                    stmt.executeUpdate();
                }

            } else {
                response.sendRedirect("errore.jsp");
                return;
            }

            response.sendRedirect("/ProgettoTSW/User/carrello.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("errore.jsp");
        }
    }
}
