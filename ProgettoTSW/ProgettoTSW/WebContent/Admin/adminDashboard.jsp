<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Amministratore</title>
    <link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>

<body>
<div class="dashboard-container">
    <h1>Benvenuto, <%= username %> (Admin)</h1>
    <p>Usa i collegamenti seguenti per accedere alle sezioni amministrative.</p>

    <div class="dashboard-links">
	<a href="adminUtenti.jsp" class="dashboard-link">Gestione Utenti</a>
	<a href="adminInserzioni.jsp" class="dashboard-link">Gestione Inserzioni</a>
    <a href="adminOrdini.jsp" class="dashboard-link">Gestione Ordini</a>
    <a href="adminSegnalazioni.jsp" class="dashboard-link">Gestione Segnalazioni</a>
    </div>
</div>

</body>
</html>
