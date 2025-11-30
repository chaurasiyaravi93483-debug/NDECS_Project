<!DOCTYPE html>
<html>
<head>
    <title>Register User - NDECS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        body {
            background:#000; color:#ffd400; margin:0;
            display:flex; justify-content:center; align-items:center;
            height:100vh; font-family:Arial;
        }
        .box {
            width:380px; background:#111; padding:30px;
            border-radius:14px; border:1px solid #222;
            box-shadow:0 0 20px rgba(255,212,0,0.15);
        }
        h2 { text-align:center; margin:0 0 20px 0; }

        label { display:block; margin-top:15px; font-size:14px; }
        input, select {
            width:100%; padding:12px; margin-top:6px;
            border-radius:6px; border:1px solid #333;
            background:#000; color:#ffd400;
        }
        button {
            width:100%; padding:12px; margin-top:22px;
            background:#ffd400; color:#000; border:none;
            font-weight:bold; border-radius:6px; cursor:pointer;
        }
        button:hover { background:#ffea33; }

        .msg { text-align:center; margin-top:15px; }
    </style>
</head>
<body>

<div class="box">
    <h2>Add User</h2>

    <form action="RegisterUserServlet" method="post">

        <label>Name</label>
        <input type="text" name="name" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <label>Role</label>
        <select name="role">
            <option value="admin">Admin</option>
            <option value="user">User</option>
        </select>

        <button type="submit">Create User</button>

        <div class="msg">
            <span style="color:lime;">
                <%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %>
            </span>
            <span style="color:red;">
                <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
            </span>
        </div>
    </form>

</div>

</body>
</html>
