<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accesso Negato</title>
    <link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>
<body>
<div class="accessoNegato">
    <h1 class="alert">Accesso Negato</h1>
    <%
        String errore = request.getParameter("errore");
        if ("auth".equals(errore)) {
    %>
        <p class="alert">Non sei autenticato</p>
    <%
        } else if ("nonadmin".equals(errore)) {
    %>
        <p class="alert">Non hai i permessi per accedere alla pagina</p>
    <%
        }
    %>
    
    <p>
        Vai alla <a href="index.jsp">Home</a> <br>
        Oppure <br>
        <ul>
        <li><a href="login.jsp">Login</a>
        <li><a href="registrazione.jsp">Registrati</a>
        </ul>
    </p>
</div>
</body>
</html>
