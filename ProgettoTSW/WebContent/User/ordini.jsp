<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>

<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>I tuoi Ordini</title>
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<%
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
    	String sql = "SELECT v.id_vendita, i.nome, v.data_vendita, v.totale, v.stato_vendita " +
                	 "FROM Vendite v JOIN Inserzioni i ON v.id_carta = i.id_inserzione " +
                	 "WHERE v.id_utente = ? ORDER BY v.data_vendita DESC";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idUtente);
        ResultSet rs = stmt.executeQuery();
%>

<h2>I tuoi Ordini</h2>
<table border="1">
    <tr><th>ID Ordine</th><th>Prodotto</th><th>Data</th><th>Totale Spesa</th><th>Stato Vendita</th><th>Azioni</th></tr>
<%
    while (rs.next()) {
    int idOrdine = rs.getInt("id_vendita");
%>
    <tr>
        <td><%= idOrdine %></td>
        <td><%= rs.getString("nome") %></td>
        <td><%= rs.getTimestamp("data_vendita") %></td>
        <td><%= rs.getDouble("totale") %></td>
        <td><%= rs.getString("stato_vendita") %></td>
        <td>
			<form action="/ProgettoTSW/AggiornaStatoOrdineServlet" method="post">
			    <input type="hidden" name="id_ordine" value="<%= idOrdine %>">
			    <select name="stato">
			        <option value="In Corso">In Corso</option>
			        <option value="Completata">Completata</option>
			        <option value="Annullata">Annullata</option>
			    </select>
			    <button type="submit">Aggiorna stato</button>
			</form>
			
        	<a href="/ProgettoTSW/User/ordine.jsp?id_vendita=<%= idOrdine %>">Dettagli Ordine</a><br>
	        <form action="/ProgettoTSW/CancellaOrdineServlet" method="post" onsubmit="return confermaEliminazioneOrd();">
			    <input type="hidden" name="id_ordine" value="<%= idOrdine %>">
			    <input type="hidden" name="origine" value="profilo">
			    <button type="submit">Elimina Ordine</button>
			</form>
		</td>
    </tr>
<%
    }
%>
</table>
<%
    } catch (Exception e) {
        out.println("Errore: " + e.getMessage());
    }
%>
