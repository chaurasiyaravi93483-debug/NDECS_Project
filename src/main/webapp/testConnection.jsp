<%@ page import="java.sql.*" %>
<%@ page import="com.ndecs.util.DBConnection" %>
<html>
<head><title>DB Test</title></head>
<body style="background:#000;color:#ffd400;">
<h2>DB Connection Test</h2>

<%
    Connection conn = null;
    try {
        conn = DBConnection.getConnection();
        if(conn != null){
%>
        <p style="color:lightgreen;">Connection successful!</p>
<%
        } else {
%>
        <p style="color:red;">Connection failed.</p>
<%
        }
    } catch(Exception e){
%>
        <p style="color:red;">Exception: <%= e.getMessage() %></p>
<%
        //e.printStackTrace();   <-- safe
    } finally {
        try { if(conn != null) conn.close(); } catch(Exception e){}
    }
%>

</body>
</html>
