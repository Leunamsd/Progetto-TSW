package it.unisa.progetto.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/CreaRecensioneServlet")
public class CreaRecensioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idUtente = (Integer) session.getAttribute("id_utente");  // recensore

        if (idUtente == null) {
            // Utente non loggato, redirect al login
            response.sendRedirect("/ProgettoTSW/Login.jsp");
            return;
        }

        try {
            int idDestinatario = Integer.parseInt(request.getParameter("id_destinatario"));
            int idTransazione = Integer.parseInt(request.getParameter("id_transazione"));
            int voto = Integer.parseInt(request.getParameter("voto"));
            String commento = request.getParameter("commento");

            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {

                String sql = "INSERT INTO Recensioni (id_utente, id_destinatario, id_transazione, voto, commento) "
                           + "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, idUtente);
                    stmt.setInt(2, idDestinatario);
                    stmt.setInt(3, idTransazione);
                    stmt.setInt(4, voto);
                    stmt.setString(5, commento);
                    stmt.executeUpdate();
                }

                
                response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idDestinatario);

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore: " + e.getMessage());
            response.sendRedirect("errore.jsp");
        }
    }
}
