package it.unisa.progetto.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class InserisciInserzioneServlet
 */
@WebServlet("/InserisciInserzioneServlet")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024)
public class InserisciInserzioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    try {
	    	Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
	        String nome = request.getParameter("nome");
	        String serie = request.getParameter("serie");
	        String rarita = request.getParameter("rarita");
	        String condizione = request.getParameter("condizione");
	        int quantita = Integer.parseInt(request.getParameter("quantita"));
	        double prezzo = Double.parseDouble(request.getParameter("prezzo"));

	        Part filePart = request.getPart("immagine");
	        if (filePart == null || filePart.getSize() == 0) {
	            throw new ServletException("File immagine non fornito.");
	        }
	        
	        InputStream immagine = filePart.getInputStream();
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
	            String sql = "INSERT INTO Inserzioni (id_utente_inserzionista, immagine, nome, serie, rarita, quantita, condizione, prezzo) " +
	                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, idUtente);
	            stmt.setBlob(2, immagine);
	            stmt.setString(3, nome);
	            stmt.setString(4, serie);
	            stmt.setString(5, rarita);
	            stmt.setInt(6, quantita);
	            stmt.setString(7, condizione);
	            stmt.setDouble(8, prezzo);
	            
	            stmt.executeUpdate();
	            
	            response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtente);
	            return;
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
	            response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?errore=" + errorMessage);
	        }

	    } catch (Exception e) {
	    	String errorMessage = URLEncoder.encode(e.getMessage(), "UTF-8");
	        response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?errore=" + errorMessage);
	    }
	}
}