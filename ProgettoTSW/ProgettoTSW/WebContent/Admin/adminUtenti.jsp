<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ include file="/Common/navbar.jsp" %>
<%
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT id_utente, username, email, ruolo FROM Utenti");
%>

<head>
    <meta charset="UTF-8">
    <title>Dashboard- Utenti</title>
    <link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<body>
<h2>Lista Utenti</h2>
<table border="1">
    <tr>
        <th>ID</th><th>Username</th><th>Email</th><th>Ruolo</th><th>Azioni</th>
    </tr>
<%
    while (rs.next()) {
%>
    <tr>
        <td><%= rs.getInt("id_utente") %></td>
        <td><%= rs.getString("username") %></td>
        <td><%= rs.getString("email") %></td>
        <td><%= rs.getString("ruolo") %></td>
        <td><form action="/ProgettoTSW/EliminaProfiloServlet" method="post" onsubmit="return confermaEliminazioneProf();">
		    <input type="hidden" name="id_utente" value="<%= rs.getInt("id_utente") %>">
		    <button type="submit">Elimina</button>
		</form></td>
    </tr>
<%
    }
    rs.close(); stmt.close(); conn.close();
%>
</table>
</body>
