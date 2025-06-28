package it.unisa.progetto.control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class modificaProfiloServlet
 */
@WebServlet("/ModificaProfiloServlet")
public class ModificaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String regione = request.getParameter("regione");
        String citta = request.getParameter("citta");
        String indirizzo = request.getParameter("indirizzo");
        String telefono = request.getParameter("telefono");
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
     // Costruisci la query dinamicamente
        StringBuilder sql = new StringBuilder("UPDATE Utenti SET ");
        List<Object> params = new ArrayList<>();
        
        if (nome != null && !nome.isEmpty() && cognome != null && !cognome.isEmpty()) {
            sql.append("nome = ?, ");
            params.add(nome + " " + cognome);
        }
        if (regione != null && !regione.isEmpty()) {
            sql.append("regione = ?, ");
            params.add(regione);
        }
        if (citta != null && !citta.isEmpty()) {
            sql.append("citta = ?, ");
            params.add(citta);
        }
        if (indirizzo != null && !indirizzo.isEmpty()) {
            sql.append("indirizzo = ?, ");
            params.add(indirizzo);
        }
        if (telefono != null && !telefono.isEmpty()) {
            sql.append("telefono = ?, ");
            params.add(telefono);
        }
        
        if (username != null && !username.isEmpty()) {
            sql.append("username = ?, ");
            params.add(username);
        }
        if (email != null && !email.isEmpty()) {
            sql.append("email = ?, ");
            params.add(email);
        }
        if (password != null && !password.isEmpty()) {
            sql.append("password = ?, ");
            params.add(hashedPassword);
        }
        
        // Se non ci sono parametri da aggiornare
        if (params.isEmpty()) {
            response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtente);
            return;
        }
        // Rimuovi l'ultima virgola e spazio
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id_utente = ?");
        params.add(idUtente);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            
            // Imposta i parametri
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            stmt.executeUpdate();
        } catch (Exception e) {
        	System.err.println("Errore durante l'aggiornamento: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect("/ProgettoTSW/Common/profilo.jsp?id=" + idUtente);
    }
}