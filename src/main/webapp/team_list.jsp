<%@ page import="java.util.*" %>
<%@ page import="com.ndecs.dao.TeamDAO" %>
<%@ page import="com.ndecs.model.Team" %>

<%
    TeamDAO dao = new TeamDAO();
    List<Team> list = dao.getAll();
%>

<%
    com.ndecs.model.User _u = (com.ndecs.model.User) session.getAttribute("user");
    if(_u == null || _u.getRole() == null || !"admin".equalsIgnoreCase(_u.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
<title>Team List</title>

<style>
    body {
        background:#000;
        color:#ffd400;
        font-family:Arial;
        margin:0;
        padding:20px;
    }

    .container {
        max-width:900px;
        margin:0 auto;
        background:#111;
        padding:20px;
        border-radius:10px;
        border:1px solid #222;
    }

    table {
        width:100%;
        border-collapse:collapse;
        color:#fff;
        margin-top:20px;
    }

    th, td {
        padding:12px;
        border-bottom:1px solid #222;
        text-align:left;
    }

    th {
        color:#ffd400;
        background:#000;
    }

    .btn {
        padding:6px 12px;
        text-decoration:none;
        border-radius:5px;
        font-weight:bold;
        cursor:pointer;
    }

    .edit { background:#ffd400; color:#000; }
    .edit:hover { background:#ffea47; }

    .delete { background:red; color:#fff; }
    .delete:hover { background:darkred; }

    .add-btn {
        background:#ffd400;
        color:#000;
        padding:10px 15px;
        border-radius:6px;
        font-weight:bold;
        text-decoration:none;
        display:inline-block;
        margin-bottom:10px;
    }

    .add-btn:hover { background:#ffea47; }

    .success {
        background:#0a0;
        color:#fff;
        padding:10px;
        border-radius:6px;
        margin-bottom:10px;
    }

    .deleted {
        background:red;
        color:#fff;
        padding:10px;
        border-radius:6px;
        margin-bottom:10px;
    }

    .offline-row td {
        color:#888;
    }

</style>

</head>
<body>

<div class="container">

    <h2>Emergency Teams</h2>

    <!-- SUCCESS MESSAGES -->
    <% if (request.getParameter("updated") != null) { %>
        <div class="success">Team updated successfully!</div>
    <% } %>

    <% if (request.getParameter("deleted") != null) { %>
        <div class="deleted">Team deleted (set to Offline)</div>
    <% } %>

    <a href="add_team.jsp" class="add-btn">+ Add New Team</a>

    <!-- IF NO TEAMS -->
    <% if(list.size() == 0) { %>
        <p style="color:#ccc;">No teams available.</p>
    <% } else { %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Phone</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>
            <% for(Team t : list) { %>

                <tr class="<%= t.getStatus().equals("Offline") ? "offline-row" : "" %>">

                    <td><%= t.getTeamId() %></td>
                    <td><%= t.getName() %></td>
                    <td><%= t.getType() %></td>
                    <td><%= t.getPhone() %></td>

                    <td><%= t.getStatus() %></td>

                    <td>

                        <!-- EDIT BUTTON ALWAYS -->
                        <a class="btn edit" href="edit_team.jsp?teamId=<%=t.getTeamId()%>">Edit</a>

                        <!-- DELETE ONLY IF NOT OFFLINE -->
                        <% if(!t.getStatus().equals("Offline")) { %>

                            <a class="btn delete"
                               href="UpdateTeamServlet?action=delete&teamId=<%=t.getTeamId()%>">
                                Delete
                            </a>

                        <% } else { %>

                            <span style="color:#999;">Deleted</span>

                        <% } %>

                    </td>

                </tr>

            <% } %>
        </tbody>

    </table>

    <% } %>

</div>

</body>
</html>
