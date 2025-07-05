<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Crea Inserzione</title>
	<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
	<script src="<%= request.getContextPath() %>/Scripts/Validazione.js"></script>
</head>
<body>
	<h3>Aggiungi una nuova inserzione</h3>
	<form action="/ProgettoTSW/InserisciInserzioneServlet" method="post" enctype="multipart/form-data" id="inserzioneForm">
		<input type="hidden" name="id_utente" value=<%= idUtente %>>
		<label>Immagine:<br><input type="file" name="immagine" accept="image/*" required></label><br>
	    <label>Nome:<br><input type="text" name="nome" required></label><br>
	    <label>Serie:<br><input type="text" name="serie"></label><br>
	    <label>Rarità:<br><input type="text" name="rarita"></label><br>
	    <label>Prezzo:<br><input type="number" step="0.01" name="prezzo" required></label><br>
	    <label>Quantità:<br><input type="number" name="quantita" min="1" value="1" required></label><br>
	    <label>Condizione:<br><input type="text" name="condizione"></label><br><br>
    	<button type="submit">Aggiungi Inserzione</button>
    	<button type="reset">Ripristina</button>
	</form>
</body>
</html>