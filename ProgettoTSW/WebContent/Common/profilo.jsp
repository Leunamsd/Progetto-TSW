<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>

<!DOCTYPE html>

<%
	    int idUtenteProfilo = Integer.parseInt(request.getParameter("id")); // Id utente proprietario del profilo
	    Connection connP = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
		String sqlP = "SELECT * FROM Utenti WHERE id_utente=?";
		PreparedStatement stmtP = connP.prepareStatement(sqlP);
		stmtP.setInt(1, idUtenteProfilo);
        ResultSet rsP = stmtP.executeQuery();
        
      //Controllo utente
    	Integer idUtenteSessione = -1;
    	boolean isLoggedIn = false;
    	idUtenteSessione = (Integer) request.getSession().getAttribute("id_utente"); // Id utente in sessione

    	if (idUtenteSessione != null) {
    	    isLoggedIn = true;
    	}
        
        if (rsP.next()) {
        	idUtente= rsP.getInt("id_utente");
            username = rsP.getString("username");
        }
        
        boolean profiloPersonale = isLoggedIn && (idUtenteProfilo == idUtenteSessione);
	%>

<head>
<meta charset="UTF-8">
<title>Cardplanet - Profilo di <%= username %></title>
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
<script src="<%= request.getContextPath() %>/Scripts/Scripts.js"></script>
</head>

<body>

	<h2>Profilo di <%= username %></h2>
	
		<%
	        String errore = request.getParameter("errore");
	        if ("insert".equals(errore)) {
	    %>
	        <p class="alert">Errore nella richiesta SQL.</p>
	        
	    <%
	        } else if("invalidInput".equals(errore)) {
	    %>
	    	<p class="alert">Errore nell'input.</p>
	    <%
	        } else if("generic".equals(errore)) {
	    %>
	    	<p class="alert">Errore nella creazione dell'inserzione.</p>
	    <%
	        }
	    %>
	    
	<% if (isLoggedIn == true) { %>
	<fieldset>
		<legend>Informazioni Utente</legend>
		<ul>
			<li>Nome: <%= rsP.getString("nome") %></li>
			<li>Regione di Residenza: <%= rsP.getString("regione") %></li>
			<li>Città di Residenza: <%= rsP.getString("citta") %></li>
			<li>Indirizzo: <%= rsP.getString("indirizzo") %></li>
			<li>Numero di Telefono: <%= rsP.getString("telefono") %></li>
		</ul>
	</fieldset>
	<% } %>
	
	<% if (profiloPersonale) { %>
    <fieldset>
		<legend>Opzioni Utente</legend>
			<ul>
				<li><a href="/ProgettoTSW/User/modificaProfilo.jsp?id=<%= idUtente %>">Modifica le informazioni del profilo</a></li>
				<li><a href="/ProgettoTSW/User/aggiungiInserzione.jsp">Crea una nuova inserziona</a>
			</ul>
	</fieldset>
	<% } %>
	
	<%
		// Voti
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
		PreparedStatement mediaStmt = conn.prepareStatement("SELECT AVG(voto) AS media FROM Recensioni WHERE id_destinatario = ?");
		mediaStmt.setInt(1, idUtente);
		ResultSet rsMedia = mediaStmt.executeQuery();
		
		if (rsMedia.next() && rsMedia.getDouble("media") != 0) {
		    out.println("<h3>Media voti: " + String.format("%.2f", rsMedia.getDouble("media")) + " / 5</h3>");
		} else {
		    out.println("<h3>Nessuna recensione ricevuta</h3>");
		}
		rsMedia.close();
		mediaStmt.close();
	%>
	
	<h3>Le tue inserzioni</h3>
	<%
	        String sql = "SELECT id_inserzione, immagine, nome, serie, rarita, prezzo, quantita, condizione FROM Inserzioni WHERE id_utente_inserzionista = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, idUtente);
	        ResultSet rs = stmt.executeQuery();
	%>
	<table border="1">
	    <tr>
	        <th>Immagine</th><th>Nome</th><th>Serie</th><th>Rarità</th>
	        <th>Prezzo</th><th>Quantità</th><th>Condizione</th><th>Azioni</th>
	    </tr>
	<%
	    while (rs.next()) {
	        int idInserzione = rs.getInt("id_inserzione");
	        Blob immagine = rs.getBlob("immagine");
	        byte[] imgData = immagine.getBytes(1, (int) immagine.length());
	        String base64Image = Base64.getEncoder().encodeToString(imgData);
	%>
	    <tr>
	    	
	        <td><a href="<%= contextPath %>/Common/inserzione.jsp?id=<%= idInserzione %>"><img src="data:image/jpeg;base64,<%= base64Image %>" alt="Immagine" width="90">
	        </a></td>
	        <td><%= rs.getString("nome") %></td>
	        <td><%= rs.getString("serie") %></td>
	        <td><%= rs.getString("rarita") %></td>
	        <td>€<%= rs.getBigDecimal("prezzo") %></td>
	        <td><%= rs.getInt("quantita") %></td>
	        <td><%= rs.getString("condizione") %></td>
	        <td>
	        	<% if (isLoggedIn && (idUtenteSessione == idUtente || "amministratore".equalsIgnoreCase(ruolo))) { %>
	            <form action="/ProgettoTSW/EliminaInserzioneServlet" method="post" onsubmit="return confermaEliminazioneIns();">
	            	<input type="hidden" name="idutente" value=<%= idUtente %>>
	                <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
	                <input type="hidden" name="origine" value="profilo">
	                <button type="submit" class="elimina-button">Elimina</button>
	            </form>
	            <form action="/ProgettoTSW/AumentaQuantitaServlet" method="post" onsubmit="return validaQuantita();">
			        <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
			        <input type="number" name="quantita_da_aggiungere" value="1" min="1">
			        <button type="submit">Aggiungi quantità</button>
			    </form>
			    <% } %>
	        </td>
	    </tr>
	<%
	    }
	%>
	</table><br>
	
	<% if(isLoggedIn && idUtenteSessione == idUtente) { %>
	<form action="/ProgettoTSW/EliminaProfiloServlet" method="post" onsubmit="return confermaEliminazioneProf();">
        <input type="hidden" name="id_utente" value="<%= idUtente %>">
        <button type="submit" class="elimina-button">Elimina il tuo profilo</button>
    </form>
    <% } %>

	<%
	boolean mostraPulsanteSegnala = isLoggedIn && (idUtenteSessione != -1) && (idUtenteSessione != idUtenteProfilo);
	
	if (mostraPulsanteSegnala) {
	%>
		<fieldset>
		<legend>Segnala Utente</legend>
		    <form action="<%= request.getContextPath() %>/SegnalaUtenteServlet" method="post">
		        <input type="hidden" name="id_utente_segnalato" value="<%= idUtenteProfilo %>">
		        <textarea name="descrizione" placeholder="Descrivi il problema..." required></textarea><br><br>
		        <button type="submit">Segnala utente</button>
		    </form>
	    </fieldset>
	<% } %>
	
	<% if (idUtenteSessione != null && idUtenteSessione != idUtenteProfilo) { %>
	<fieldset>
		<legend>Recensisci Utente</legend>
	    <form action="/ProgettoTSW/AggiungiRecensioneServlet" method="post" id="recensioneForm">
	        <input type="hidden" name="id_destinatario" value="<%= idUtenteProfilo %>">
	        <label>Voto (1-5):<br><input type="number" name="voto" id="voto" min="1" max="5" required></label><br>
	        <label>Commento:<br><textarea name="commento" required></textarea></label><br>
	        <button type="submit">Invia Recensione</button>
	    </form>
	</fieldset>
	<% } else if (idUtenteSessione == null) { %>
	    <p><a href="/ProgettoTSW/Login.jsp">Effettua il login per scrivere una recensione</a></p>
	<% } %>
	
	<h3>Recensioni</h3>
	
	<%
	PreparedStatement ps = conn.prepareStatement(
	  "SELECT r.id_recensione, r.id_utente, r.voto, r.commento, r.data_creazione, u.username " +
	  "FROM Recensioni r JOIN Utenti u ON r.id_utente = u.id_utente " +
	  "WHERE id_transazione IN (SELECT id_vendita FROM Vendite WHERE id_utente = ?)");
	
	while (rs.next()) {
	    int idRecensione = rs.getInt("id_recensione");
	    int idAutore = rs.getInt("id_utente");
	%>
	  <div class="recensione">
	    <p><strong><%= rs.getString("username") %></strong> ha scritto il <%= rs.getTimestamp("data_creazione") %>:</p>
	    <p>Voto: <%= rs.getInt("voto") %> / 5</p>
	    <p>Commento: <%= rs.getString("commento") %></p>
	
	    <% if (isLoggedIn && (idUtenteSessione == idAutore || "amministratore".equalsIgnoreCase(ruolo))) { %>
	      <form action="/ProgettoTSW/EliminaRecensioneServlet" method="post" class="elimina-form" onsubmit="return confermaEliminazioneRec();">
	        <input type="hidden" name="id_recensione" value="<%= idRecensione %>">
	        <button type="submit" class="elimina-button">Elimina</button>
	      </form>
	    <% } %>
	  </div>
	  <hr>
	<%
	}
	rs.close();
	ps.close();
	%>
</body>
