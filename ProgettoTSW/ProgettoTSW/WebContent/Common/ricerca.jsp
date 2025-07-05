<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/Common/navbar.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>
<%
    String query = request.getParameter("query");

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Ufficio@039!");
        String sql = "SELECT * FROM Inserzioni WHERE nome LIKE ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + query + "%");

        rs = stmt.executeQuery();
%>

<h2>Risultati per "<%= query %>"</h2>
<ul>
<% while (rs.next()) { 
	
	Blob immagine = rs.getBlob("immagine");
    byte[] imgData = immagine.getBytes(1, (int) immagine.length());
    String base64Image = Base64.getEncoder().encodeToString(imgData);
	
%>
	
    <li>
        <a href="inserzione.jsp?id=<%= rs.getInt("id_inserzione") %>">
        	<img src="data:image/jpeg;base64,<%= base64Image %>" alt="Immagine" width="90"><br>
            <%= rs.getString("nome") %> - €<%= rs.getDouble("prezzo") %>
        </a>
    </li>
<% } %>
</ul>

<%
    } catch (Exception e) {
        out.println("Errore: " + e.getMessage());
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
%>
