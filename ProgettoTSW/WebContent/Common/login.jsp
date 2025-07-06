<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>Cardplanet - Login</title>
    <link rel="stylesheet" type="text/css" href="../Styles/stile.css">
</head>
<body>
    <h2>Accedi al tuo account</h2>
    
    <%
        String errore = request.getParameter("errore");
        if ("cred".equals(errore)) {
    %>
        <p class="alert">Credenziali errate. Riprova.</p>
        
    <%
        } else if(errore != null) {
    %>
    	<p class="alert">Errore: <%= errore %></p>
    <%
        }
    %>

    <form action="/ProgettoTSW/LoginServlet" method="post">
        <label>Username:<br><input type="text" name="username" required>
        </label><br><br>
        <label>Password:<br><input type="password" name="password" id="inputPass" required>
        </label><br><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>
