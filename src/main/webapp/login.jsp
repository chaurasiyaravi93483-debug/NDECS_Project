<!DOCTYPE html>
<html>
<head>
    <title>Login - NDECS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        body {
            background:#000; color:#ffd400;
            font-family:Arial; margin:0;
            height:100vh; display:flex;
            justify-content:center; align-items:center;
        }

        .box {
            width:360px;
            background:#111;
            padding:30px;
            border-radius:14px;
            border:1px solid #222;
            box-shadow:0 0 20px rgba(255,212,0,0.15);
        }

        h2 { margin-top:0; text-align:center; }

        label { display:block; margin-top:15px; font-size:14px; }

        input {
            width:100%; padding:12px; margin-top:6px;
            border-radius:6px; border:1px solid #333;
            background:#000; color:#ffd400; font-size:15px;
        }

        button {
            width:100%; padding:12px; margin-top:22px;
            background:#ffd400; color:#000; border:none;E
            font-weight:bold; border-radius:6px;
            font-size:16px; cursor:pointer;
        }
        button:hover { background:#ffea33; }

        .link {
            margin-top:10px; text-align:center;
        }
        .link a { color:#ffd400; text-decoration:none; }
        .link a:hover { color:white; }

        .msg { text-align:center; margin-top:15px; }
    </style>
</head>
<body>

<div class="box">
    <h2>Login</h2>

    <form action="LoginServlet" method="post">

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">LOGIN</button>

        <div class="msg">
            <span style="color:red;">
                <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
            </span>
        </div>

        <div class="link">
            <a href="forgot_password.jsp">Forgot Password?</a>
        </div>

    </form>
</div>

</body>
</html>
