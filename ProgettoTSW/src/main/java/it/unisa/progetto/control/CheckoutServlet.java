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
import java.sql.ResultSet;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
		
		String totStr = request.getParameter("totale");
		double tot = Double.parseDouble(totStr);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {

            // Prendi le carte dal carrello
            String selectSql = "SELECT id_carta, quantita FROM Carrello WHERE id_utente = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, idUtente);
            ResultSet rs = selectStmt.executeQuery();

            // Inserisci ogni carta in Vendite
            String insertSql = "INSERT INTO Vendite (id_utente, id_carta, totale, stato_vendita) VALUES (?, ?, ?, 'In corso')";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);

            while (rs.next()) {
                int idCarta = rs.getInt("id_carta");
                int quantita = rs.getInt("quantita");

                for (int i = 0; i < quantita; i++) {
                    insertStmt.setInt(1, idUtente);
                    insertStmt.setInt(2, idCarta);
                    insertStmt.setDouble(3, tot);
                    insertStmt.executeUpdate();
                }
            }

            // Svuota il carrello
            String deleteSql = "DELETE FROM Carrello WHERE id_utente = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, idUtente);
            deleteStmt.executeUpdate();

            response.sendRedirect("/ProgettoTSW/User/ordini.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore: " + e.getMessage());
            response.sendRedirect("errore.jsp");
        }
    }
}