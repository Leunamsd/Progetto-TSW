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
import java.sql.ResultSet;

/**
 * Servlet implementation class AggiungiAlCarrelloServlet
 */
@WebServlet("/AggiungiAlCarrelloServlet")
public class AggiungiAlCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
		int idInserzione = Integer.parseInt(request.getParameter("id_inserzione"));
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");

            // Verifica se già presente nel carrello
            String checkSql = "SELECT quantita FROM Carrello WHERE id_utente = ? AND id_carta = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, idUtente);
            stmt.setInt(2, idInserzione);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int quantita = rs.getInt("quantita") + 1;
                rs.close();
                stmt.close();

                // aggiorna quantità
                String updateSql = "UPDATE Carrello SET quantita = ? WHERE id_utente = ? AND id_carta = ?";
                stmt = conn.prepareStatement(updateSql);
                stmt.setInt(1, quantita);
                stmt.setInt(2, idUtente);
                stmt.setInt(3, idInserzione);
                stmt.executeUpdate();
                
            } else {
                rs.close();
                stmt.close();

                // inserisci nuovo
                String insertSql = "INSERT INTO Carrello (id_utente, id_carta, quantita) VALUES (?, ?, 1)";
                stmt = conn.prepareStatement(insertSql);
                stmt.setInt(1, idUtente);
                stmt.setInt(2, idInserzione);
                stmt.executeUpdate();
            }
            
            // Riduci la quantità dell'inserzione
            String updateInserzioneSql = "UPDATE Inserzioni SET quantita = quantita - 1 WHERE id_inserzione = ? AND quantita > 0";
            stmt = conn.prepareStatement(updateInserzioneSql);
            stmt.setInt(1, idInserzione);
            stmt.executeUpdate();

            response.sendRedirect("/ProgettoTSW/User/carrello.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/User/carrello.jsp?errore=" + errorMessage);
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}