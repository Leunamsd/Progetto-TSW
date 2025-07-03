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

            Class.forName("com.mysql.cj.jdbc.Driver");

            if ("rimuovi".equals(azione)) {
                int idInserzione = Integer.parseInt(request.getParameter("id_inserzione"));

                // Recupera la quantità da restituire
                String selectSql = "SELECT quantita FROM Carrello WHERE id_utente = ? AND id_carta = ?";
                try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
                    stmt.setInt(1, idUtente);
                    stmt.setInt(2, idInserzione);
                    var rs = stmt.executeQuery();

                    if (rs.next()) {
                        int quantita = rs.getInt("quantita");
                        rs.close();

                        // Aggiorna quantità inserzione
                        String updateSql = "UPDATE Inserzioni SET quantita = quantita + ? WHERE id_inserzione = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, quantita);
                            updateStmt.setInt(2, idInserzione);
                            updateStmt.executeUpdate();
                        }

                        // Elimina dal carrello
                        String deleteSql = "DELETE FROM Carrello WHERE id_utente = ? AND id_carta = ?";
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                            deleteStmt.setInt(1, idUtente);
                            deleteStmt.setInt(2, idInserzione);
                            deleteStmt.executeUpdate();
                        }

                    } else {
                        rs.close();
                        response.sendRedirect("errore.jsp");
                        return;
                    }
                }

            } else if ("svuota".equals(azione)) {
                // Recupera tutte le righe del carrello
                String selectSql = "SELECT id_carta, quantita FROM Carrello WHERE id_utente = ?";
                try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
                    stmt.setInt(1, idUtente);
                    var rs = stmt.executeQuery();

                    while (rs.next()) {
                        int idInserzione = rs.getInt("id_carta");
                        int quantita = rs.getInt("quantita");

                        // Aggiorna quantità inserzione
                        String updateSql = "UPDATE Inserzioni SET quantita = quantita + ? WHERE id_inserzione = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, quantita);
                            updateStmt.setInt(2, idInserzione);
                            updateStmt.executeUpdate();
                        }
                    }
                    rs.close();
                }

                // Elimina tutto il carrello
                String deleteSql = "DELETE FROM Carrello WHERE id_utente = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, idUtente);
                    deleteStmt.executeUpdate();
                }

            } else {
                response.sendRedirect("errore.jsp");
                return;
            }

            response.sendRedirect("/ProgettoTSW/User/carrello.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore: " + e.getMessage());
            response.sendRedirect("errore.jsp");
        }
    }
}
