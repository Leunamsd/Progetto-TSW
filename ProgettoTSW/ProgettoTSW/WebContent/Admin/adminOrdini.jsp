<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ include file="/Common/navbar.jsp" %>
<%
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
    String sql = "SELECT v.id_vendita, v.id_utente, u.username, v.id_carta, i.nome, v.data_vendita, v.stato_vendita " +
                 "FROM Vendite v " +
                 "JOIN Utenti u ON v.id_utente = u.id_utente " +
                 "JOIN Inserzioni i ON v.id_carta = i.id_inserzione";
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
   	
%>

<head>
    <meta charset="UTF-8">
    <title>Dashboard - Ordini</title>
    <link rel="stylesheet" type="text/css" href="../Styles/stile.css">
    <script src="<%= request.getContextPath() %>/Scripts/Scripts.js"></script>
</head>

<body>
<h2>Lista Ordini</h2>
<table border="1">
    <tr>
        <th>ID Vendita</th><th>Utente</th><th>Prodotto</th><th>Data</th><th>Stato</th><th>Azioni</th>
    </tr>
<%
    while (rs.next()) {
    	
    int idOrdine = rs.getInt("id_vendita");
%>
    <tr>
        <td><%= idOrdine %></td>
        <td><%= rs.getString("username") %></td>
        <td><%= rs.getString("nome") %></td>
        <td><%= rs.getTimestamp("data_vendita") %></td>
        <td><%= rs.getString("stato_vendita") %></td>
        <td><form action="EliminaOrdineServlet" method="post" onsubmit="return confermaEliminazioneOrd();">
		    <input type="hidden" name="id_ordine" value="<%= idOrdine %>">
		    <input type="hidden" name="origine" value="profilo">
		    <button type="submit">Elimina Ordine</button>
		</form></td>
        
    </tr>
<%
    }
    rs.close(); stmt.close(); conn.close();
%>
</table>
</body>
