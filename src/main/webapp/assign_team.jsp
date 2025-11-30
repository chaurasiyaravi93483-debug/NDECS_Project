<%@ page import="com.ndecs.model.Incident" %>
<%@ page import="com.ndecs.model.Team" %>
<%@ page import="java.util.*" %>

<%
    Incident inc = (Incident) request.getAttribute("incident");
    List<Team> teams = (List<Team>) request.getAttribute("teams");

    if (inc == null) {
        response.sendRedirect("DashboardServlet?error=notfound");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Assign Team</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">

    <style>
        body {
            background:#000;
            color:#ffd400;
            font-family:Arial;
            margin:0;
        }

        .container {
            max-width:600px;
            margin:35px auto;
            padding:25px;
            background:#111;
            border-radius:12px;
            border:1px solid #222;
            box-shadow:0 0 12px rgba(255,212,0,0.20);
        }

        h2 { margin:0 0 10px 0; }

        label {
            display:block;
            margin-top:15px;
            font-size:14px;
            font-weight:bold;
        }

        select, input[type=text] {
            width:100%;
            padding:12px;
            background:#000;
            color:#ffd400;
            border:1px solid #444;
            border-radius:6px;
            margin-top:5px;
        }

        button {
            margin-top:20px;
            padding:12px;
            width:100%;
            background:#ffd400;
            color:#000;
            font-size:15px;
            border:none;
            border-radius:8px;
            font-weight:bold;
            cursor:pointer;
        }

        button:hover { background:#ffeb3b; }

        a.back {
            color:#ffd400;
            text-decoration:none;
            font-weight:bold;
            margin-bottom:15px;
            display:inline-block;
        }
    </style>
</head>

<body>

<div class="container">

    <a class="back" href="IncidentDetailsServlet?id=<%= inc.getIncidentId() %>">⬅ Back</a>

    <h2>Assign Team to Incident</h2>

    <p><b>Incident:</b> <%= inc.getTitle() %></p>

    <form action="AssignmentServlet" method="post">

        <!-- Hidden Incident ID -->
        <input type="hidden" name="incidentId" value="<%= inc.getIncidentId() %>">

        <!-- Team dropdown -->
        <label>Select Team</label>
        <select name="teamId" required>
            <% for(Team t : teams) { %>
                <option value="<%= t.getTeamId() %>">
                    <%= t.getName() %> ( <%= t.getStatus() %> )
                </option>
            <% } %>
        </select>

        <button type="submit">Assign Team</button>
    </form>

</div>

</body>
</html>
