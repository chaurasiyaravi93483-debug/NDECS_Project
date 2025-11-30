<%@ page import="com.ndecs.dao.TeamDAO" %>
<%@ page import="com.ndecs.model.Team" %>

<%
    String tid = request.getParameter("teamId");

    if (tid == null || tid.trim().equals("")) {
        response.sendRedirect("team_list.jsp?error=invalid");
        return;
    }

    int id = Integer.parseInt(tid);

    TeamDAO dao = new TeamDAO();
    Team t = dao.getById(id);

    if (t == null) {
        response.sendRedirect("team_list.jsp?error=notfound");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Team</title>

    <style>
        body {
            background:#000;
            color:#ffd400;
            font-family:Arial;
            margin:0;
            padding:20px;
        }

        .container {
            max-width:420px;
            margin:0 auto;
            background:#111;
            padding:25px;
            border-radius:10px;
            border:1px solid #222;
        }

        input, select {
            width:100%;
            padding:10px;
            margin:10px 0;
            border:1px solid #333;
            border-radius:6px;
            background:#000;
            color:#ffd400;
        }

        button {
            width:100%;
            padding:10px;
            background:#ffd400;
            color:#000;
            border:none;
            border-radius:6px;
            font-weight:bold;
            cursor:pointer;
        }

        button:hover { background:#ffee40; }

        a { color:#ffd400; text-decoration:none; }
    </style>

</head>
<body>

<div class="container">

    <h2>Edit Team</h2>

    <form action="UpdateTeamServlet" method="post">

        <input type="hidden" name="teamId" value="<%=t.getTeamId()%>">

        <label>Team Name</label>
        <input type="text" name="name" value="<%=t.getName()%>" required>

        <label>Type</label>
        <input type="text" name="type" value="<%=t.getType()%>" required>

        <label>Phone</label>
        <input type="text" name="phone" value="<%=t.getPhone()%>" required>

        <label>Status</label>
        <select name="status">
            <option value="Active" <%= t.getStatus().equals("Active") ? "selected" : "" %>>Active</option>
            <option value="Offline" <%= t.getStatus().equals("Offline") ? "selected" : "" %>>Offline</option>
        </select>

        <button type="submit">Update Team</button>

    </form>

    <br>
    <a href="team_list.jsp">← Back to Team List</a>

</div>

</body>
</html>
