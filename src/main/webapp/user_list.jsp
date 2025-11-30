<%@ page import="java.util.*" %>
<%@ page import="com.ndecs.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">

    <style>
        body {
            background:#000;
            color:#ffd400;
            font-family:Arial;
            margin:0;
        }

        .wrap {
            max-width:1200px;
            margin:30px auto;
            padding:20px;
            background:#111;
            border-radius:10px;
            border:1px solid #222;
        }

        h2 { margin:0 0 15px 0; }

        .topbar {
            display:flex;
            justify-content:space-between;
            margin-bottom:15px;
            flex-wrap:wrap;
            gap:10px;
        }

        .add-btn {
            background:#ffd400;
            color:#000;
            padding:10px 16px;
            border-radius:6px;
            font-weight:bold;
            text-decoration:none;
        }

        .search-box, .filter {
            padding:10px;
            background:#000;
            border:1px solid #333;
            border-radius:6px;
            color:#ffd400;
        }

        table {
            width:100%;
            border-collapse:collapse;
            background:#111;
            border:1px solid #222;
        }

        th,td {
            padding:12px;
            border-bottom:1px solid #222;
            text-align:left;
        }

        th {
            background:#000;
            color:#ffd400;
        }

        tr:hover {
            background:#1a1a1a;
        }

        .btn {
            padding:6px 10px;
            text-decoration:none;
            font-weight:bold;
            border-radius:6px;
            font-size:13px;
        }

        .edit { background:#00c3ff; color:#000; }
        .delete { background:#ff4444; color:#fff; }

        .badge {
            padding:6px 10px;
            border-radius:6px;
            font-weight:bold;
        }

        .admin { background:#ff4444; color:#fff; }
        .agency { background:#00c3ff; color:#000; }
        .user { background:#00bb55; color:#000; }

    </style>

    <script>
        function filterUsers() {
            let search = document.getElementById("search").value.toLowerCase();
            let role = document.getElementById("roleFilter").value;
            let rows = document.getElementsByClassName("user-row");

            for (let r of rows) {
                let name = r.getAttribute("data-name").toLowerCase();
                let email = r.getAttribute("data-email").toLowerCase();
                let roleVal = r.getAttribute("data-role");

                let matchSearch = name.includes(search) || email.includes(search);
                let matchRole = (role === "All" || role.toLowerCase() === roleVal.toLowerCase());

                r.style.display = (matchSearch && matchRole) ? "" : "none";
            }
        }
    </script>

</head>

<body>

<div class="wrap">

    <h2>User Management</h2>

    <div class="topbar">
        <a class="add-btn" href="register_user.jsp">+ Add User</a>

        <input id="search" class="search-box" type="text"
               placeholder="Search name/email..."
               onkeyup="filterUsers()">

        <select id="roleFilter" class="filter" onchange="filterUsers()">
            <option>All</option>
            <option>admin</option>
            <option>agency</option>
            <option>user</option>
        </select>
    </div>

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Created</th>
            <th>Actions</th>
        </tr>

        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if(users == null || users.size() == 0){
        %>

        <tr><td colspan="6" style="text-align:center; color:#777;">No users found</td></tr>

        <% } else { 
            for(User u : users){ %>

        <tr class="user-row"
            data-name="<%=u.getName()%>"
            data-email="<%=u.getEmail()%>"
            data-role="<%=u.getRole()%>">

            <td><%= u.getUserId() %></td>
            <td><%= u.getName() %></td>
            <td><%= u.getEmail() %></td>

            <td>
                <span class="badge <%=u.getRole()%>"><%=u.getRole()%></span>
            </td>

            <td><%= u.getCreatedAt() %></td>

            <td>
                <a class="btn edit" href="EditUserServlet?userId=<%=u.getUserId()%>">Edit</a>
                <a class="btn delete" onclick="return confirm('Delete user?');"
                   href="DeleteUserServlet?userId=<%=u.getUserId()%>">Delete</a>
            </td>

        </tr>

        <% } } %>
    </table>

</div>

</body>
</html>
