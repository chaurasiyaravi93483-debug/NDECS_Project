<%@ page import="com.ndecs.model.Incident" %>

<%
    Incident inc = (Incident) request.getAttribute("incident");

    if (inc == null) {
        response.sendRedirect("dashboard.jsp?error=notfound");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <title>Edit Incident</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    
    <style>
        body {
            background:#000; color:#ffd400;
            font-family:Arial; margin:0;
        }

        .container {
            max-width:650px;
            margin:30px auto;
            padding:25px;
            background:#111;
            border-radius:12px;
            border:1px solid #222;
            box-shadow:0 0 12px rgba(255,212,0,0.15);
        }

        h2 { margin-top:0; color:#ffd400; }

        label {
            display:block;
            margin:10px 0 4px;
            font-weight:bold;
            font-size:14px;
        }

        input, select, textarea {
            width:100%;
            padding:12px;
            margin-bottom:8px;
            background:#000;
            border:1px solid #333;
            border-radius:6px;
            color:#ffd400;
            font-size:14px;
        }

        textarea { height:130px; }

        button {
            margin-top:15px;
            width:100%;
            padding:12px;
            background:#ffd400;
            color:#000;
            border:none;
            border-radius:8px;
            font-weight:bold;
            font-size:15px;
            cursor:pointer;
        }

        button:hover { background:#ffea33; }
    </style>
</head>

<body>

<div class="container">

    <h2>Edit Incident</h2>

    <form action="EditIncidentServlet" method="post">

        <input type="hidden" name="incidentId" value="<%= inc.getIncidentId() %>">

        <!-- Title -->
        <label>Title</label>
        <input type="text" name="title" value="<%= inc.getTitle() %>" required>

        <!-- Type -->
        <label>Type</label>
        <select name="type" required>
            <option value="Fire" <%= inc.getType().equals("Fire")?"selected":"" %>>Fire</option>
            <option value="Accident" <%= inc.getType().equals("Accident")?"selected":"" %>>Accident</option>
            <option value="Medical" <%= inc.getType().equals("Medical")?"selected":"" %>>Medical</option>
            <option value="Crime" <%= inc.getType().equals("Crime")?"selected":"" %>>Crime</option>
            <option value="Rescue" <%= inc.getType().equals("Rescue")?"selected":"" %>>Rescue</option>
        </select>

        <!-- Severity -->
        <label>Severity</label>
        <select name="severity" required>
            <option value="Low" <%= inc.getSeverity().equals("Low")?"selected":"" %>>Low</option>
            <option value="Medium" <%= inc.getSeverity().equals("Medium")?"selected":"" %>>Medium</option>
            <option value="High" <%= inc.getSeverity().equals("High")?"selected":"" %>>High</option>
        </select>

        <!-- Status -->
        <label>Status</label>
        <select name="status" required>
            <option value="Pending" <%= inc.getStatus().equals("Pending")?"selected":"" %>>Pending</option>
            <option value="Assigned" <%= inc.getStatus().equals("Assigned")?"selected":"" %>>Assigned</option>
            <option value="InProgress" <%= inc.getStatus().equals("InProgress")?"selected":"" %>>InProgress</option>
            <option value="Resolved" <%= inc.getStatus().equals("Resolved")?"selected":"" %>>Resolved</option>
        </select>

        <!-- Location -->
        <label>Location</label>
        <input type="text" name="location" value="<%= inc.getLocation() %>" required>

        <!-- Description -->
        <label>Description</label>
        <textarea name="description"><%= inc.getDescription() %></textarea>

        <button type="submit">Update Incident</button>
    </form>
</div>

</body>
</html>
