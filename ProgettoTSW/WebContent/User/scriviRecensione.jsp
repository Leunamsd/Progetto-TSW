<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<%
Integer idUtente = (Integer) session.getAttribute("id_utente");
if (idUtente == null) {
  response.sendRedirect("login.jsp");
  return;
}
String idTransazione = request.getParameter("id_transazione");
%>

<form action="/ProgettoTSW/CreaRecensioneServlet" method="post">
  <input type="hidden" name="id_transazione" value="<%= idTransazione %>">
  <label>Voto (1-5):</label>
  <input type="number" name="voto" min="1" max="5" required><br>
  <label>Commento:</label><br>
  <textarea name="commento" required></textarea><br>
  <input type="submit" value="Invia recensione">
</form>
