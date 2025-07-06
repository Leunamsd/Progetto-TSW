<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
    String contextPath = request.getContextPath();
	
	Integer idUtente = (Integer) request.getSession().getAttribute("id_utente");
    String username = (String) session.getAttribute("username");
    String ruolo = (String) session.getAttribute("ruolo");
%>
<div id="header">
    <img src="<%= contextPath %>/images/CP.png" width="130" height="130" alt="Logo">
    
    <div id="navbar">
        <a href="<%= contextPath %>/Common/index.jsp">Home</a>
        <% if (username != null) { %>
            | <a href="<%= contextPath %>/Common/profilo.jsp?id=<%= idUtente %>">Profilo</a>
            | <a href="<%= contextPath %>/User/ordini.jsp">Ordini</a>
            | <a href="<%= contextPath %>/User/wishlist.jsp">Wishlist</a>
            | <a href="<%= contextPath %>/User/carrello.jsp">Carrello</a>
            <% if ("amministratore".equalsIgnoreCase(ruolo)) { %>
                | <a href="<%= contextPath %>/Admin/Test.jsp">Test Admin</a>
                | <a href="<%= contextPath %>/Admin/adminDashboard.jsp">Dashboard Admin</a>
            <% } %>
            | <a href="<%= contextPath %>/LogoutServlet">Logout</a>
        <% } else { %>
            | <a href="<%= contextPath %>/Common/login.jsp">Login</a>
            | <a href="<%= contextPath %>/Common/registrazione.jsp">Registrati</a>
        <% } %>
    </div>
</div>
