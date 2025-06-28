<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>

<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<%
int idVendita = Integer.parseInt(request.getParameter("id_vendita"));
int idUtenteSessione = (int) session.getAttribute("id_utente");

Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
PreparedStatement ps = conn.prepareStatement("SELECT v.*, i.* FROM Vendite v JOIN Inserzioni i ON v.id_carta = i.id_inserzione WHERE v.id_vendita = ? AND v.id_utente = ?");
ps.setInt(1, idVendita);
ps.setInt(2, idUtenteSessione);
ResultSet rs = ps.executeQuery();

double totale = 0;
%>

<h2>Dettagli ordine</h2>
<div class="inserzioni">
<%
    while (rs.next()) {
        int idInserzione = rs.getInt("id_carta");
        String nome = rs.getString("nome");
        double prezzo = rs.getDouble("prezzo");
        Blob immagine = rs.getBlob("immagine");
        totale = rs.getDouble("totale");

        byte[] imgData = immagine.getBytes(1, (int) immagine.length());
        String base64Image = Base64.getEncoder().encodeToString(imgData);
%>
    <div class="dettagliInserzione">
        <a href="/ProgettoTSW/Common/inserzione.jsp?id=<%= idInserzione %>">
            <img src="data:image/jpeg;base64,<%= base64Image %>" alt="Immagine" width="150"><br>
            <strong><%= nome %></strong><br>
            €<%= prezzo %>
        </a>
    </div>
<%
    }
%>
</div>

<p>Totale: <%= totale %></p>

  <%
  // Controllo se esiste già una recensione per questa vendita
  PreparedStatement check = conn.prepareStatement("SELECT * FROM Recensioni WHERE id_transazione = ?");
  check.setInt(1, idVendita);
  ResultSet rsCheck = check.executeQuery();

  if (rsCheck.next()) {
  %>
    <p><strong>Hai già lasciato una recensione per questo ordine.</strong></p>
    <p>Voto: <%= rsCheck.getInt("voto") %> / 5</p>
    <p>Commento: <%= rsCheck.getString("commento") %></p>
  <%
  } else {
  %>
    <jsp:include page="scriviRecensione.jsp">
      <jsp:param name="id_transazione" value="<%= idVendita %>" />
    </jsp:include>
  <%
  }
  rsCheck.close(); check.close();
  %>
  
<%
rs.close(); ps.close();
%>
