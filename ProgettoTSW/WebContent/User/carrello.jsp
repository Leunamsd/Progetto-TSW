<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>

<head>
<meta charset="UTF-8">
<title>Il tuo Carrello</title>
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

	<%
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	try {
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
	
	    String sql = "SELECT c.id_carrello, c.id_carta AS id_inserzione, i.immagine, i.nome, i.prezzo, c.quantita " +
	                 "FROM Carrello c JOIN Inserzioni i ON c.id_carta = i.id_inserzione " +
	                 "WHERE c.id_utente = ?";
	
	    stmt = conn.prepareStatement(sql);
	    stmt.setInt(1, idUtente);
	    rs = stmt.executeQuery();
	
	    double totale = 0;
	    int idCarrello = 0;
	
	%>
	    <h2>🛒 Il tuo carrello</h2>
	    <table border="1">
	        <tr><th>Prodotto</th><th>Prezzo</th><th>Quantità</th><th>Totale</th><th>Azioni</th></tr>
	
	<%
	    while (rs.next()) {
	        int idInserzione = rs.getInt("id_inserzione");
	        Blob immagine = rs.getBlob("immagine");
	        byte[] imgData = immagine.getBytes(1, (int) immagine.length());
	        String base64Image = Base64.getEncoder().encodeToString(imgData);
	        String nome = rs.getString("nome");
	        double prezzo = rs.getDouble("prezzo");
	        int quantita = rs.getInt("quantita");
	        double subtotale = prezzo * quantita;
	        
	        totale += subtotale;
	        idCarrello = rs.getInt("id_carrello");
	%>
	        <tr>
	            <td><a href="<%= contextPath %>/Common/inserzione.jsp?id=<%= idInserzione %>"><img src="data:image/jpeg;base64,<%= base64Image %>" alt="Immagine" width="90">
	        	</a></td>
	            <td>€<%= prezzo %></td>
	            <td><%= quantita %></td>
	            <td>€<%= subtotale %></td>
	            <td><form action="/ProgettoTSW/GestisciCarrelloServlet" method="post" onsubmit="return confermaEliminazioneIns();">
	                <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
	                <input type="hidden" name="azione" value="rimuovi">
	                <button type="submit" class="elimina-button">Elimina</button>
	            </form></td>
	        </tr>
	<%
	    }
	%>
	        <tr>
	            <td colspan="3" style="text-align:right"><strong>Totale:</strong></td>
	            <td><strong>€<%= totale %></strong></td>
	        </tr>
	    </table>
	    
	    <br>
	    <form action="/ProgettoTSW/CheckoutServlet" method="post">
	    	<input type="hidden" name="totale" value="<%= totale %>">
	        <button type="submit">Checkout</button>
	    </form>
	    <form action="/ProgettoTSW/GestisciCarrelloServlet" method="post">
		  	<input type="hidden" name="azione" value="svuota">
		  	<button type="submit">Svuota carrello</button>
		</form><br>
	    
	    
	<%
	} catch (Exception e) {
	    out.println("Errore: " + e.getMessage());
	    e.printStackTrace();
	} finally {
	    if (rs != null) rs.close();
	    if (stmt != null) stmt.close();
	    if (conn != null) conn.close();
	}
	%>
