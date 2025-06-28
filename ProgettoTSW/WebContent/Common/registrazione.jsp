<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cardplanet - Registrazione</title>
		<link rel="stylesheet" type="text/css" href="../Styles/stile.css">
	</head>
	<body>
		
		<h2>Inserisci i tuoi dati per registrarti</h2>
		
		<form action="/ProgettoTSW/RegistrazioneServlet" method="post">
			<fieldset>
			<legend>Informazioni Personali</legend>
		    <label>Nome:<br><input type="text" name="nome" required></label><br>
		    <label>Cognome:<br><input type="text" name="cognome" required></label><br>
		    <label>Regione:<br>
		    <select name="regione" required>
		    	<option value="Abruzzo">Abruzzo</option>
		    	<option value="Basilicata">Basilicata</option>
		    	<option value="Calabra">Calabria</option>
		    	<option value="Campania">Campania</option>
		    	<option value="Emiliaromagna">Emilia Romagna</option>
		    	<option value="Friuli">Friuli Venezia Giulia</option>
		    	<option value="Lazio">Lazio</option>
		    	<option value="Liguria">Liguria</option>
		    	<option value="Lombardia">Lombardia</option>
		    	<option value="Marche">Marche</option>
		    	<option value="Molise">Molise</option>
		    	<option value="Piemonte">Piemonte</option>
		    	<option value="Puglia">Puglia</option>
		    	<option value="Sardegna">Sardegna</option>
		    	<option value="Sicilia">Sicilia</option>
		    	<option value="Toscana">Toscana</option>
		    	<option value="Trentino">Trentino Alto Adige</option>
		    	<option value="Umbria">Umbria</option>
		    	<option value="Valle d'Aosta">Valle d'Aosta</option>
		    	<option value="Veneto">Veneto</option>
		    </select></label><br>
		    <label>Città:<br><input type="text" name="citta" required></label><br>
		    <label>Indirizzo:<br><input type="text" name="indirizzo" required></label><br>
		    <label>Telefono:<br><input type="tel" name="telefono" required></label><br>
		    </fieldset>
		    <fieldset>
		    <legend>Dati Utente</legend>
		    <label>Username:<br><input type="text" name="username" required></label><br>
		    <label>Email:<br><input type="email" name="email" required></label><br>
		    <label>Password:<br><input type="password" name="password" id="inputPass" required></label>
		    </fieldset><br>
	    	<input type="submit" value="Registrati">
		</form>

	</body>
</html>