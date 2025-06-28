<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.Base64" %>
<%@ include file="/Common/navbar.jsp" %>
<%
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
    String sql = "SELECT * FROM Inserzioni";
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
%>

<head>
    <meta charset="UTF-8">
    <title>Dashboard - Utenti</title>
    <link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<body>
<h2>Lista Inserzioni</h2>
<table border="1">
    <tr>
        <th>Immagine</th><th>Nome</th><th>Serie</th><th>Rarità</th><th>Prezzo</th><th>Quantità</th><th>Condizione</th><th>Azioni</th>
    </tr>
<%
    while (rs.next()) {
    	int idInserzione = rs.getInt("id_inserzione");
    	int idUtenteInserzionista = rs.getInt("id_utente_inserzionista");
        byte[] imgData = rs.getBlob("immagine").getBytes(1, (int) rs.getBlob("immagine").length());
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
        <td><form action="/ProgettoTSW/EliminaInserzioneServlet" method="post" onsubmit="return confirm('Eliminare questa inserzione?');">
		    <input type="hidden" name="id_inserzione" value="<%= idInserzione %>">
		    <input type="hidden" name="id_utente" value="<%= idUtenteInserzionista %>">
		    <input type="hidden" name="origine" value="admin">
		    <button type="submit" class="elimina-button">Elimina</button>
		</form></td>
    </tr>
<%
    }
    rs.close(); stmt.close(); conn.close();
%>
</table>
</body>
