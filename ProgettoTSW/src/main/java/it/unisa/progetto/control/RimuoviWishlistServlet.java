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

@WebServlet("/RimuoviWishlistServlet")
public class RimuoviWishlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
		int idInserzione = Integer.parseInt(request.getParameter("id_inserzione"));

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
            String sql = "DELETE FROM Wishlist WHERE id_utente = ? AND id_inserzione = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUtente);
            stmt.setInt(2, idInserzione);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/User/wishlist.jsp?errore=" + errorMessage);
        }

        response.sendRedirect("/ProgettoTSW/User/wishlist.jsp");
    }
}