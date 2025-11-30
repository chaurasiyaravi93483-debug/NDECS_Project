<%@ page import="com.ndecs.model.User" %>
<%
    User user = (User) request.getAttribute("user");

    // ✅ Prevent NullPointerException
    if(user == null){
        response.sendRedirect("UserListServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        body { background:#000; color:#ffd400; font-family:Arial; margin:0; }
        .container {
            max-width:600px; margin:30px auto; padding:25px;
            background:#111; border-radius:10px; border:1px solid #222;
        }
        h2 { margin:0 0 15px 0; }
        label { display:block; margin:12px 0 4px; font-size:14px; }
        input, select {
            width:100%; padding:10px; background:#000;
            border:1px solid #444; border-radius:6px;
            color:#ffd400; font-size:14px;
        }
        button {
            width:100%; padding:12px; background:#ffd400;
            color:#000; border:none; border-radius:6px;
            font-weight:bold; margin-top:20px;
        }
        button:hover { background:#ffea33; cursor:pointer; }
        a { color:#ffd400; text-decoration:none; }
    </style>
</head>

<body>

<div class="container">

    <h2>Edit User</h2>

    <form action="EditUserServlet" method="post">

        <input type="hidden" name="userId" value="<%= user.getUserId() %>">

        <label>Name</label>
        <input type="text" name="name" value="<%= user.getName() %>" required>

        <label>Email</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" required>

        <label>Password</label>
        <input type="text" name="password" value="<%= user.getPassword() %>" required>

        <label>Role</label>
        <select name="role">
            <option value="admin" <%= user.getRole().equals("admin")?"selected":"" %>>Admin</option>
            <option value="agency" <%= user.getRole().equals("agency")?"selected":"" %>>Agency</option>
            <option value="user" <%= user.getRole().equals("user")?"selected":"" %>>User</option>
        </select>

        <button type="submit">Update User</button>
    </form>

    <br>
    <a href="UserListServlet">⬅ Back to User List</a>

</div>

</body>
</html>
