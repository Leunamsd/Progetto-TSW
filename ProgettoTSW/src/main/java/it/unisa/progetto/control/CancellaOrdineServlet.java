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
 * Servlet implementation class CancellaOrdineServlet
 */
@WebServlet("/CancellaOrdineServlet")
public class CancellaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
		int idUtente = (int) request.getSession().getAttribute("id_utente");
        String idOrdineStr = request.getParameter("id_ordine");
        String origine = request.getParameter("origine");

        try {
            int idOrdine = Integer.parseInt(idOrdineStr);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
            
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Vendite WHERE id_vendita = ?");
            ps.setInt(1, idOrdine);
            ps.executeUpdate();

            ps.close();
            conn.close();

            // Redirect a seconda dell'origine
            if ("adminOrdini".equalsIgnoreCase(origine)) {
                response.sendRedirect("adminOrdini.jsp");
            } else {
                response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtente);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtente + "&errore=" + errorMessage);
        }
    }
}