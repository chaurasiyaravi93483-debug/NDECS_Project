<%@ page import="java.util.*" %>
<%@ page import="com.ndecs.model.Incident" %>

<%
    // receive list from servlet
    List<Incident> incidents = (List<Incident>) request.getAttribute("incidents");
    if (incidents == null) incidents = new ArrayList<>();

    // use variable names that don't clash with JSP implicit 'page'
    Integer pageAttr = (Integer) request.getAttribute("page");
    Integer totalAttr = (Integer) request.getAttribute("totalPages");

    int currentPage = (pageAttr == null) ? 1 : pageAttr;
    int totalPagesInt = (totalAttr == null) ? 1 : totalAttr;

    String q = (String) request.getAttribute("q");
    String type = (String) request.getAttribute("type");
    String severity = (String) request.getAttribute("severity");
    String status = (String) request.getAttribute("status");
    String from = (String) request.getAttribute("from");
    String to = (String) request.getAttribute("to");
%>

<!DOCTYPE html>
<html>
<head>
    <title>All Incidents - NDECS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        body { background:#000; color:#ffd400; font-family:Arial; margin:0; }
        header { background:#111; padding:15px 20px; border-bottom:1px solid #222; display:flex; justify-content:space-between; align-items:center; position:sticky; top:0; z-index:10; }
        header h2 { margin:0; font-size:26px; }
        nav a { color:#ffd400; margin-left:16px; text-decoration:none; font-weight:bold; }
        nav a:hover { color:#fff; }

        .container { max-width:1200px; margin:20px auto; padding:20px; }

        .filter-box { background:#111; padding:14px; border-radius:10px; border:1px solid #222; margin-bottom:18px; }
        .filter-box input, .filter-box select { padding:8px 10px; margin-right:8px; margin-bottom:6px; background:#000; color:#ffd400; border:1px solid #333; border-radius:6px; }
        .filter-btn { background:#ffd400; color:#000; padding:8px 12px; border:none; border-radius:6px; cursor:pointer; font-weight:bold; }

        table { width:100%; border-collapse:collapse; background:#111; border:1px solid #222; }
        th, td { padding:12px; border-bottom:1px solid #222; text-align:left; }
        th { background:#000; color:#ffd400; }
        tr:hover { background:#1a1a1a; }

        .badge { padding:6px 12px; border-radius:6px; font-weight:bold; display:inline-block; }
        .Pending { background:#ffd400; color:#000; }
        .Assigned { background:#0044ff; color:#fff; }
        .InProgress { background:#ffaa00; color:#000; }
        .Resolved { background:#00bb55; color:#000; }

        .btn { padding:6px 10px; border-radius:6px; text-decoration:none; font-weight:bold; margin-right:6px; display:inline-block; }
        .btn-view { background:#333; color:#ffd400; }
        .btn-edit { background:#00c3ff; color:#000; }
        .btn-assign { background:#ffd400; color:#000; }
        .btn-start { background:#ffaa00; color:#000; }
        .btn-resolve { background:#00bb55; color:#000; }
        .btn-reset { background:#ff4444; color:#fff; }

        .pagination { text-align:center; margin-top:16px; }
        .pagination a { padding:8px 12px; margin:4px; background:#111; color:#ffd400; border:1px solid #333; border-radius:6px; text-decoration:none; }
        .pagination a.active { background:#ffd400; color:#000; border-color:#ffd400; }
        .pagination a:hover { background:#ffea33; color:#000; }
    </style>
</head>

<body>
<header>
    <h2>NDECS - All Incidents</h2>
    <nav>
        <a href="DashboardServlet">Dashboard</a>
        <a href="report_incident.jsp">Report Incident</a>
        <a href="add_team.jsp">Add Team</a>
        <a href="AssignmentHistoryServlet">Assignments</a>
    </nav>
</header>

<div class="container">
    <div class="filter-box">
        <form action="AllIncidentsServlet" method="get">
            <input type="text" name="q" placeholder="Search title or description" value="<%= q==null? "": q %>">
            <select name="type">
                <option value="">Type</option>
                <option <%= "Fire".equals(type)?"selected":"" %>>Fire</option>
                <option <%= "Crime".equals(type)?"selected":"" %>>Crime</option>
                <option <%= "Medical".equals(type)?"selected":"" %>>Medical</option>
            </select>
            <select name="severity">
                <option value="">Severity</option>
                <option <%= "Low".equals(severity)?"selected":"" %>>Low</option>
                <option <%= "Medium".equals(severity)?"selected":"" %>>Medium</option>
                <option <%= "High".equals(severity)?"selected":"" %>>High</option>
            </select>
            <select name="status">
                <option value="">Status</option>
                <option <%= "Pending".equals(status)?"selected":"" %>>Pending</option>
                <option <%= "Assigned".equals(status)?"selected":"" %>>Assigned</option>
                <option <%= "InProgress".equals(status)?"selected":"" %>>InProgress</option>
                <option <%= "Resolved".equals(status)?"selected":"" %>>Resolved</option>
            </select>

            <input type="date" name="from" value="<%= from==null? "": from %>">
            <input type="date" name="to" value="<%= to==null? "": to %>">

            <button class="filter-btn" type="submit">Apply</button>
            <button class="filter-btn" type="button" onclick="window.location='AllIncidentsServlet'">Reset</button>
        </form>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th><th>Title</th><th>Type</th><th>Severity</th><th>Status</th><th>Reported At</th><th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <% for (Incident inc : incidents) { %>
            <tr>
                <td><%= inc.getIncidentId() %></td>
                <td><%= inc.getTitle() %></td>
                <td><%= inc.getType() %></td>
                <td><%= inc.getSeverity() %></td>
                <td><span class="badge <%= inc.getStatus() %>"><%= inc.getStatus() %></span></td>
                <td><%= inc.getReportedAt() %></td>
                <td>
                    <a class="btn btn-view" href="IncidentDetailsServlet?id=<%= inc.getIncidentId() %>">View</a>
                    <a class="btn btn-edit" href="EditIncidentServlet?incidentId=<%= inc.getIncidentId() %>">Edit</a>
                    <a class="btn btn-assign" href="PrepareAssignServlet?incidentId=<%= inc.getIncidentId() %>">Assign</a>
                    <br>
                    <a class="btn btn-start" href="StatusServlet?action=inprogress&id=<%= inc.getIncidentId() %>">Start</a>
                    <a class="btn btn-resolve" href="StatusServlet?action=resolved&id=<%= inc.getIncidentId() %>">Resolve</a>
                    <a class="btn btn-reset" href="StatusServlet?action=pending&id=<%= inc.getIncidentId() %>">Reset</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>

    <div class="pagination">
        <% for (int i = 1; i <= totalPagesInt; i++) { %>
            <a href="AllIncidentsServlet?page=<%= i %>" class="<%= (i==currentPage) ? "active" : "" %>"><%= i %></a>
        <% } %>
    </div>
</div>
</body>
</html>
