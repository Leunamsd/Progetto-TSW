<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>

<!DOCTYPE html>

<%
    int idInserzione = Integer.parseInt(request.getParameter("id"));

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
        String sql1 = "SELECT * FROM Inserzioni WHERE id_inserzione = ?";
        PreparedStatement stmt1 = conn.prepareStatement(sql1);
        stmt1.setInt(1, idInserzione);
        ResultSet rs = stmt1.executeQuery();

        if (rs.next()) {
        	int idUtenteInserzionista = rs.getInt("id_utente_inserzionista");
            String nome = rs.getString("nome");
            String condizione = rs.getString("condizione");
            String descrizione = rs.getString("serie") + " - " + rs.getString("rarita");
            double prezzo = rs.getDouble("prezzo");
            Blob immagine = rs.getBlob("immagine");
            int quantitaDisponibile = rs.getInt("quantita");

            byte[] imgData = immagine.getBytes(1, (int) immagine.length());
            String base64Image = Base64.getEncoder().encodeToString(imgData);
            
            String sql2 = "SELECT username FROM Utenti WHERE id_utente = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setInt(1, idUtenteInserzionista);
            ResultSet rsU = stmt2.executeQuery();
            
%>

<head>
<meta charset="UTF-8">
<title>eCommerce - <%= nome %></title>
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
<script src="<%= request.getContextPath() %>/Scripts/Scripts.js"></script>
</head>

	<%
	//Se utente non è loggato
    idUtente = -1;
    Object idObj = session.getAttribute("id_utente");
    if (idObj != null) {
        try {
            idUtente = Integer.parseInt(idObj.toString());
        } catch (NumberFormatException e) {
            idUtente = -1;
        }
    }

	
	if (ruolo == null) {
	    ruolo = "";
	}
	
    if (rsU.next()) {
        username = rsU.getString("username");
    }
	%>

	<p>Venduto da:
	  <a href="<%= contextPath %>/Common/profilo.jsp?id=<%= idUtenteInserzionista %>">
	    <%= username %>
	  </a>
	</p>
	<h2><%= nome %></h2>
	<img src="data:image/jpeg;base64,<%= base64Image %>" width="225"><br>
	<p>Condizioni: <%= condizione %></p>
	<p><%= descrizione %></p>
	<p>Disponibilità: <%= quantitaDisponibile %> pezzi</p>
	<p>Prezzo: €<%= prezzo %></p>
	
	<!-- Pulsanti per tutti -->
	<form action="/ProgettoTSW/AggiungiWishlistServlet" method="post">
	        <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
	        <button type="submit">Aggiungi alla Wishlist</button>
	</form>
	<% if (quantitaDisponibile > 0 && idUtenteInserzionista != idUtente) { %>
	    <form action="/ProgettoTSW/AggiungiAlCarrelloServlet" method="post">
	        <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
	        <button type="submit">Aggiungi al carrello</button>
	    </form>
	<% } else if (quantitaDisponibile == 0) { %>
	    <p class="alert">Prodotto esaurito</p>
	<% } %>
	
	<% if (idUtente == idUtenteInserzionista || ruolo.equals("amministratore")) { %>
    <form action="/ProgettoTSW/EliminaInserzioneServlet" method="post" onsubmit="return confermaEliminazioneIns();">
        <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
        <button type="submit" class="elimina-button">Rimuovi l'inserzione</button>
    </form>

    <!-- Form per aumentare quantità -->
    <form action="/ProgettoTSW/AumentaQuantitaServlet" method="post" onsubmit="return validaQuantita();">
        <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
        <input type="number" name="quantita_da_aggiungere" id="quantita_da_aggiungere" value="1" min="1">
        <button type="submit">Aggiungi quantità</button>
    </form>
<% } %>

<%
        }
    }
%>
