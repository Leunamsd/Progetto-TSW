package it.unisa.progetto.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String nomeCompleto = nome.concat(" " + cognome);
        String regione = request.getParameter("regione");
        String citta = request.getParameter("citta");
        String indirizzo = request.getParameter("indirizzo");
        String telefono = request.getParameter("telefono");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());


        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");

            String sql = "INSERT INTO utenti (nome, regione, citta, indirizzo, telefono, username, email, password) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeCompleto);
            stmt.setString(2, regione);
            stmt.setString(3, citta);
            stmt.setString(4, indirizzo);
            stmt.setString(5, telefono);
            stmt.setString(6, username);
            stmt.setString(7, email);
            stmt.setString(8, hashedPassword);

            int rows = stmt.executeUpdate();
            
            String sqlID = "SELECT id_utente, username, password, ruolo FROM utenti WHERE username = ?";
            PreparedStatement stmtID = conn.prepareStatement(sqlID);
            stmtID.setString(1, username);

            ResultSet rs = stmtID.executeQuery();

            if (rs.next()) {
            	
            	int id_utenteRS = rs.getInt("id_utente");
                String usernameRS = rs.getString("username");
                String passwordRS = rs.getString("password");
                String ruolo = rs.getString("ruolo");

                if (username.equals(usernameRS) && BCrypt.checkpw(password, passwordRS)) {
                    //crea sessione
                    HttpSession session = request.getSession();
                    session.setAttribute("id_utente", id_utenteRS);
                    session.setAttribute("username", usernameRS);
                    session.setAttribute("ruolo", ruolo);
                }
            }

            if (rows > 0) {
                response.sendRedirect("/ProgettoTSW/Common/index.jsp?registrato=ok");
            } else {
                out.println("<h2>Errore durante la registrazione.</h2>");
            }

            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            out.println("<h2>Errore: " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        }
    }
}