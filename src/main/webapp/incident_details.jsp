<%@ page import="com.ndecs.model.Incident" %>
<%@ page import="com.ndecs.model.Assignment" %>
<%@ page import="java.util.*" %>

<%
    Incident inc = (Incident) request.getAttribute("incident");
    List<Assignment> history = (List<Assignment>) request.getAttribute("history");

    // ✅ SAFETY CHECK (Fix 500 error)
    if (inc == null) {
        response.sendRedirect("DashboardServlet?error=invalid_access");
        return;
    }
    if(history == null) history = new ArrayList<>();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Incident Details - NDECS</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <style>
        body { background:#000; color:#ffd400; font-family:Arial; margin:0; }
        .container { max-width:1100px; margin:20px auto; padding:20px;
                     background:#111; border-radius:10px; border:1px solid #222; }
        .badge { padding:6px 12px; border-radius:6px; font-weight:bold; display:inline-block; }
        .Pending { background:#444; color:#ffd400; }
        .Assigned { background:#0055ff; color:#fff; }
        .InProgress { background:#ffaa00; color:#000; }
        .Resolved { background:#00bb55; color:#000; }
    </style>
</head>

<body>

<div class="container">

    <h2>Incident Details</h2>
    <a class="back-btn" href="DashboardServlet">⬅ Back</a>

    <div class="card">
        <h3><%= inc.getTitle() %></h3>
        <span>Type: <%= inc.getType() %></span><br>
        <span>Severity: <%= inc.getSeverity() %></span><br>
        <span>Status: <span class="badge <%= inc.getStatus() %>"><%= inc.getStatus() %></span></span><br>
        <span>Location: <%= inc.getLocation() %></span><br>
        <span>Reported At: <%= inc.getReportedAt() %></span><br>
        <span>Description: <%= inc.getDescription() %></span><br>

        <div class="action-buttons">
            <a href="PrepareAssignServlet?incidentId=<%= inc.getIncidentId() %>">Assign Team</a>
            <a href="EditIncidentServlet?incidentId=<%= inc.getIncidentId() %>">Edit Incident</a>
        </div>
    </div>

    <h3>Assignment Timeline</h3>

    <table>
        <thead>
        <tr>
            <th>Team</th>
            <th>Status</th>
            <th>Assigned At</th>
            <th>Assigned By</th>
        </tr>
        </thead>

        <tbody>
        <% for(Assignment a : history) { %>
            <tr>
                <td><%= (a.getTeamName()!=null) ? a.getTeamName() : "Team #" + a.getTeamId() %></td>
                <td><span class="badge <%= a.getStatus() %>"><%= a.getStatus() %></span></td>
                <td><%= a.getAssignedAt() %></td>
                <td><%= a.getAssignedBy() %></td>
            </tr>
        <% } %>
        </tbody>
    </table>

    <% if(history.size()==0){ %>
        <p>No assignments yet.</p>
    <% } %>
    <a class="btn" href="AssignmentHistoryServlet?incidentId=<%= inc.getIncidentId() %>">
    📌 Assignment History
</a>

    

</div>

</body>
</html>
