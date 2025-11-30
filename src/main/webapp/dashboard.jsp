<%@ page import="java.util.*" %>
<%@ page import="com.ndecs.model.Incident" %>
<%@ page import="com.ndecs.model.User" %>

<%
    List<Incident> incidents = (List<Incident>) request.getAttribute("recent");
    if (incidents == null) incidents = new ArrayList<>();

    User user = (User) session.getAttribute("user");
    String role = (user != null) ? user.getRole() : "";
%>

<!DOCTYPE html>
<html>
<head>
    <title>NDECS Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        body {
            margin:0;
            background:#000;
            color:#ffd400;
            font-family:Arial, sans-serif;
        }

        /* HEADER */
        header {
            background:#0d0d0d;
            padding:15px 20px;
            border-bottom:1px solid #333;
            display:flex;
            justify-content:space-between;
            align-items:center;
            box-shadow:0 0 20px rgba(255,212,0,0.15);
        }

        header h2 {
            margin:0;
            font-size:28px;
            letter-spacing:1px;
        }

        nav a {
            color:#ffd400;
            text-decoration:none;
            margin-left:18px;
            font-size:15px;
            font-weight:bold;
            padding:6px 10px;
            border-radius:6px;
            transition:0.25s;
        }
        nav a:hover {
            background:#ffd400;
            color:#000;
        }

        .container {
            padding:25px;
            max-width:1300px;
            margin:auto;
        }

        /* STAT CARDS */
        .cards {
            display:grid;
            grid-template-columns:repeat(auto-fit, minmax(220px,1fr));
            gap:20px;
            margin-bottom:30px;
        }

        .card {
            background:#111;
            padding:22px;
            border-radius:12px;
            border:1px solid #222;
            box-shadow:0 0 20px rgba(255,212,0,0.10);
            transition:0.25s;
        }

        .card:hover {
            transform:translateY(-5px);
            box-shadow:0 0 30px rgba(255,212,0,0.25);
        }

        .card h4 { margin:0; color:#bbb; font-size:16px; }
        .card .count { font-size:35px; font-weight:bold; margin-top:8px; }

        /* TABLE */
        table {
            width:100%;
            border-collapse:collapse;
            background:#0d0d0d;
            border:1px solid #222;
            border-radius:8px;
            overflow:hidden;
        }

        th {
            padding:12px;
            background:#000;
            color:#ffd400;
            text-transform:uppercase;
            font-size:13px;
            border-bottom:1px solid #333;
        }

        td {
            padding:12px;
            color:#fff;
            border-bottom:1px solid #222;
        }

        tr:hover { background:#1a1a1a; }

        /* BUTTONS */
        .btn-view, .btn-edit, .btn-assign, .btn-status {
            display:inline-block;
            padding:6px 10px;
            margin:2px 0;
            border-radius:6px;
            font-size:13px;
            font-weight:bold;
            text-decoration:none;
            transition:0.2s;
        }

        .btn-view { background:#333; color:#ffd400; }
        .btn-view:hover { background:#4d4d4d; }

        .btn-edit { background:#00c3ff; color:#000; }
        .btn-edit:hover { background:#3ad6ff; }

        .btn-assign { background:#ffd400; color:#000; }
        .btn-assign:hover { background:#ffe34a; }

        .progress { background:#ffaa00; color:#000; }
        .resolved { background:#00bb55; color:#000; }
        .reset { background:#ff4444; color:#fff; }

        /* STATUS BADGES */
        .badge {
            padding:6px 12px;
            border-radius:8px;
            font-weight:bold;
            display:inline-block;
            font-size:13px;
        }

        .Pending { background:#ffd400; color:#000; }
        .Assigned { background:#0055ff; color:#fff; }
        .InProgress { background:#ffaa00; color:#000; }
        .Resolved { background:#00bb55; color:#000; }

        /* MOBILE */
        @media (max-width: 760px) {
            nav a { margin-left:10px; font-size:13px; }
            th, td { font-size:12px; padding:8px; }
        }
    </style>

</head>
<body>

<header>
    <h2>NDECS Dashboard</h2>

    <nav>
       <% if("admin".equalsIgnoreCase(role)) { %>
    <a href="add_team.jsp">Add Team</a>
    <a href="report_incident.jsp">Report Incident</a>
    <a href="AllIncidentsServlet">All Incidents</a>
    <a href="AssignmentHistoryServlet">Assignments</a>
    <a href="UserListServlet">Manage Users</a>
    <a href="register_user.jsp">Add User</a>
<% } else { %>
    <a href="AllIncidentsServlet">All Incidents</a>
<% } %>


        <a href="LogoutServlet" style="color:#ff4444;">Logout</a>
    </nav>
</header>

<div class="container">

    <!-- STAT CARDS -->
    <div class="cards">
        <div class="card">
            <h4>Total Incidents</h4>
            <div class="count"><%= incidents.size() %></div>
        </div>

        <div class="card">
            <h4>Pending</h4>
            <div class="count"><%= incidents.stream().filter(i->i.getStatus().equals("Pending")).count() %></div>
        </div>

        <div class="card">
            <h4>Assigned</h4>
            <div class="count"><%= incidents.stream().filter(i->i.getStatus().equals("Assigned")).count() %></div>
        </div>

        <div class="card">
            <h4>Resolved</h4>
            <div class="count"><%= incidents.stream().filter(i->i.getStatus().equals("Resolved")).count() %></div>
        </div>
    </div>

    <h3 style="margin:10px 0 15px 0;">Recent Incidents</h3>

    <!-- TABLE -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Type</th>
            <th>Severity</th>
            <th>Status</th>
            <th>Time</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <% for(Incident inc : incidents) { %>
            <tr>
                <td><%= inc.getIncidentId() %></td>
                <td><%= inc.getTitle() %></td>
                <td><%= inc.getType() %></td>
                <td><%= inc.getSeverity() %></td>

                <td><span class="badge <%= inc.getStatus() %>"><%= inc.getStatus() %></span></td>
                <td><%= inc.getReportedAt() %></td>

                <td>
                    <a class="btn-view" href="IncidentDetailsServlet?id=<%= inc.getIncidentId() %>">View</a>

                    <% if("admin".equalsIgnoreCase(role)) { %>
                        <a class="btn-edit" href="EditIncidentServlet?incidentId=<%= inc.getIncidentId() %>">Edit</a>
                        <a class="btn-assign" href="PrepareAssignServlet?incidentId=<%= inc.getIncidentId() %>">Assign</a>
                    <% } %>

                    <br>

                    <a class="btn-status progress"
                       href="StatusServlet?action=inprogress&id=<%= inc.getIncidentId() %>">Start</a>

                    <a class="btn-status resolved"
                       href="StatusServlet?action=resolved&id=<%= inc.getIncidentId() %>">Resolve</a>

                    <a class="btn-status reset"
                       href="StatusServlet?action=pending&id=<%= inc.getIncidentId() %>">Reset</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>

</div>

</body>
</html>
