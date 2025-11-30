<%@ page import="java.util.*" %>
<%@ page import="com.ndecs.model.Assignment" %>

<%
    List<Assignment> list = (List<Assignment>) request.getAttribute("assignments");
    if (list == null) list = new ArrayList<>();
%>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Assignment History - NDECS</title>

    <style>
        body{ margin:0; background:#000; color:#ffd400; font-family:Arial; }
        .wrap{ max-width:1200px; margin:20px auto; padding:20px; background:#111; border:1px solid #222; border-radius:8px; }
        h2{ margin:0 0 12px 0; }

        .topnav a{ color:#ffd400; text-decoration:none; font-weight:bold; margin-right:14px; }
        .topnav a:hover{ text-decoration:underline; }

        table{ width:100%; border-collapse:collapse; }
        th, td{ padding:10px; border-bottom:1px solid #222; }
        th{ background:#000; color:#ffd400; }

        .badge{ padding:6px 10px; border-radius:6px; font-weight:bold; }
        .Assigned{ background:#0055ff; color:#fff; }
        .InProgress{ background:#ffaa00; color:#000; }
        .Resolved{ background:#00bb55; color:#000; }
        .Pending{ background:#444; color:#ffd400; }
        .link{ color:#00c3ff; text-decoration:none; }
    </style>
</head>
<body>

<div class="wrap">
    <h2>Assignment History</h2>

    <div class="topnav">
        <a href="DashboardServlet">Dashboard</a>
        <a href="AllIncidentsServlet">All Incidents</a>
        <a href="AssignmentHistoryServlet">Refresh</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>Assign ID</th>
            <th>Incident</th>
            <th>Team</th>
            <th>Assigned At</th>
            <th>By Admin ID</th>
            <th>Status</th>
        </tr>
        </thead>

        <tbody>
        <% for(Assignment a : list) { %>
            <tr>
                <td><%= a.getAssignId() %></td>
                <td><a class="link" href="IncidentDetailsServlet?id=<%=a.getIncidentId()%>">Incident #<%= a.getIncidentId() %></a></td>
                <td><%= (a.getTeamName()!=null ? a.getTeamName() : "Team #"+a.getTeamId()) %></td>
                <td><%= a.getAssignedAt() %></td>
                <td><%= a.getAssignedBy() %></td>
                <td><span class="badge <%= a.getStatus() %>"><%= a.getStatus() %></span></td>
            </tr>
        <% } %>
        </tbody>
    </table>

    <% if(list.size() == 0){ %>
        <p style="color:#ccc;">No assignments found.</p>
    <% } %>

</div>

</body>
</html>
