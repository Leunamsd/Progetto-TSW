<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ page session="true" %>
<%@ page import="java.sql.*, java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cardplanet - Home</title>
    <link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>
	<body>

	<h1>Benvenuto nel nostro eCommerce</h1>

	<%
        String registrato = request.getParameter("registrato");
        if ("ok".equals(registrato)) {
    %>
        <p>Registrazione effettuata!</p>
    <%
        }
    %>

<hr>

<h2>I nostri prodotti</h2>

<form action="ricerca.jsp" method="get">
    <input type="text" name="query" placeholder="Cerca un prodotto...">
    <input type="submit" value="Cerca">
</form>

<%
		Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!"); {
        String sql = "SELECT id_inserzione, nome, prezzo, immagine FROM Inserzioni ORDER BY RAND() LIMIT 8";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
%>

<h2>Scopri le inserzioni</h2>

<div class="inserzioni">
<%
    while (rs.next()) {
        int idInserzione = rs.getInt("id_inserzione");
        String nome = rs.getString("nome");
        double prezzo = rs.getDouble("prezzo");
        Blob immagine = rs.getBlob("immagine");

        byte[] imgData = immagine.getBytes(1, (int) immagine.length());
        String base64Image = Base64.getEncoder().encodeToString(imgData);
%>
    <div class="dettagliInserzione">
        <a href="inserzione.jsp?id=<%= idInserzione %>">
            <img src="data:image/jpeg;base64,<%= base64Image %>" alt="Immagine" width="150"><br>
            <strong><%= nome %></strong><br>
            €<%= prezzo %>
        </a>
    </div>
<%
    }
}
%>
</div>

</body>
</html>
