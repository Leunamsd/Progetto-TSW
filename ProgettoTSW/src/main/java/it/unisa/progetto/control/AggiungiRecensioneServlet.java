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

@WebServlet("/AggiungiRecensioneServlet")
public class AggiungiRecensioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idUtenteAutore = (Integer) request.getSession().getAttribute("id_utente");
        String votoStr = request.getParameter("voto");
        String commento = request.getParameter("commento");
        String idDestinatarioStr = request.getParameter("id_destinatario");

        if (idUtenteAutore == null || votoStr == null || commento == null || idDestinatarioStr == null) {
            response.sendRedirect("errore.jsp");
            return;
        }

        int voto = Integer.parseInt(votoStr);
        int idDestinatario = Integer.parseInt(idDestinatarioStr);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
            String sql = "INSERT INTO Recensioni (id_utente, id_destinatario, voto, commento) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idUtenteAutore);
                stmt.setInt(2, idDestinatario);
                stmt.setInt(3, voto);
                stmt.setString(4, commento);
                stmt.executeUpdate();
            }

            response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idDestinatario);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idDestinatario + "&errore=" + errorMessage);
        }
    }
}

