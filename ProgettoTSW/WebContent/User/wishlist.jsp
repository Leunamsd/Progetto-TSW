<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>

<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>La tua Wishlist</title>
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<h2>La tua Wishlist</h2>

	<%
    String errore = request.getParameter("errore");
    if (errore != null) {
	%>
	        <p class="alert">Errore: <%= errore %></p>
	<%
	    }
	%>

<%
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
        String sql = "SELECT i.id_inserzione, i.immagine, i.nome, i.prezzo, i.serie, i.rarita FROM Inserzioni i " +
                     "JOIN Wishlist w ON i.id_inserzione = w.id_carta WHERE w.id_utente = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idUtente);
        ResultSet rs = stmt.executeQuery();
%>
<table border="1">
        <tr><th>Prodotto</th><th>Nome</th><th>Prezzo</th><th>Serie</th><th>Rarità</th><th>Azioni</th></tr>
<%
    while (rs.next()) {
    	
    	Blob immagine = rs.getBlob("immagine");
    	byte[] imgData = immagine.getBytes(1, (int) immagine.length());
        String base64Image = Base64.getEncoder().encodeToString(imgData);
%>
    <tr>
    	<td><a href="<%= contextPath %>/Common/inserzione.jsp?id=<%= rs.getInt("id_inserzione") %>">
    	<img src="data:image/jpeg;base64,<%= base64Image %>" alt="Immagine" width="90"></a>
        <td><%= rs.getString("nome") %></td>
        <td>€<%= rs.getBigDecimal("prezzo") %></td>
        <td><%= rs.getString("serie") %></td>
        <td><%= rs.getString("rarita") %></td>
        <td>
            <form action="/ProgettoTSW/RimuoviWishlistServlet" method="post">
                <input type="hidden" name="id_inserzione" value="<%= rs.getInt("id_inserzione") %>">
                <input type="hidden" name="idutente" value=<%= idUtente %>>
                <button type="submit">Rimuovi</button>
            </form>
        </td>
    </tr>
<%
    }
%>
</table>
<%
    } catch (Exception e) {
    	System.err.println("Errore SQL: " + e.getMessage());
        out.println("Errore nel caricamento della wishlist.");
    }
%>
