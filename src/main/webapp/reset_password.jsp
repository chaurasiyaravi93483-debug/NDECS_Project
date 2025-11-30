<!DOCTYPE html>
<html>
<head>
    <title>Reset Password - NDECS</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">

    <style>
        body {
            background:#000; color:#ffd400;
            font-family:Arial; margin:0;
            display:flex; justify-content:center;
            align-items:center; height:100vh;
        }
        .box {
            width:360px; background:#111;
            padding:30px; border-radius:12px;
            border:1px solid #222;
            box-shadow:0 0 18px rgba(255,212,0,0.15);
        }
        h2 { text-align:center; margin-top:0; }

        label { display:block; margin-top:14px; }

        input {
            width:100%; margin-top:6px; padding:12px;
            border-radius:6px; border:1px solid #333;
            background:#000; color:#ffd400;
        }

        button {
            width:100%; padding:12px; margin-top:20px;
            background:#ffd400; color:#000;
            font-weight:bold; border:none; border-radius:6px;
        }
        button:hover { background:#ffea33; }
    </style>
</head>
<body>

<div class="box">
    <h2>Reset Password</h2>

    <form action="ResetPasswordServlet" method="post">

        <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">

        <label>New Password</label>
        <input type="password" name="password" required>

        <button type="submit">Update Password</button>

    </form>

</div>

</body>
</html>
