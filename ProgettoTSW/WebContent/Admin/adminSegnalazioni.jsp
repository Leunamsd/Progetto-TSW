<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>Dashboard - Segnalazioni</title>
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<body>
<h2>📋 Segnalazioni</h2>

<table border="1">
<tr>
    <th>ID</th><th>Segnalante</th><th>Segnalato</th><th>Data</th><th>Descrizione</th><th>Stato</th><th>Azioni</th>
</tr>

<%
try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!")) {
    String sql = "SELECT s.*, u1.username AS segnalante, u2.username AS segnalato FROM Segnalazioni s " +
                 "JOIN Utenti u1 ON s.id_utente_segnalante = u1.id_utente " +
                 "JOIN Utenti u2 ON s.id_utente_segnalato = u2.id_utente";
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
%>
<tr>
    <td><%= rs.getInt("id_segnalazione") %></td>
    <td><%= rs.getString("segnalante") %></td>
    <td><%= rs.getString("segnalato") %></td>
    <td><%= rs.getTimestamp("data_segnalazione") %></td>
    <td><%= rs.getString("descrizione") %></td>
    <td><%= rs.getString("stato") %></td>
    <td>
        <form action="/ProgettoTSW/AggiornaSegnalazioneServlet" method="post" style="display:inline;">
            <input type="hidden" name="id_segnalazione" value="<%= rs.getInt("id_segnalazione") %>">
            <select name="stato">
                <option value="aperta" <%= rs.getString("stato").equals("aperta") ? "selected" : "" %>>Aperta</option>
                <option value="in lavorazione" <%= rs.getString("stato").equals("in lavorazione") ? "selected" : "" %>>In lavorazione</option>
                <option value="chiusa" <%= rs.getString("stato").equals("chiusa") ? "selected" : "" %>>Chiusa</option>
            </select>
            <button type="submit">Aggiorna</button>
        </form>
        <form action="/ProgettoTSW/EliminaSegnalazioneServlet" method="post" style="display:inline;">
            <input type="hidden" name="id_segnalazione" value="<%= rs.getInt("id_segnalazione") %>">
            <button type="submit" class="elimina-button">Elimina</button>
        </form>
    </td>
</tr>
<%
    }
} catch (Exception e) {
    e.printStackTrace();
}
%>
</table>
</body>
