<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password - NDECS</title>
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

        label { margin-top:10px; display:block; }
        input {
            width:100%; margin-top:5px; padding:12px;
            border-radius:6px; border:1px solid #333;
            background:#000; color:#ffd400;
        }
        button {
            width:100%; padding:12px; margin-top:20px;
            background:#ffd400; color:#000;
            border:none; border-radius:6px; font-weight:bold;
        }
        button:hover { background:#ffea33; }

        .msg { text-align:center; margin-top:12px; }
    </style>
</head>
<body>

<div class="box">
    <h2>Forgot Password</h2>

    <form action="ForgotPasswordServlet" method="post">
        <label>Email</label>
        <input type="email" name="email" required>

        <button type="submit">Send Reset Link</button>

        <div class="msg">
            <span style="color:lime;"><%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %></span>
            <span style="color:red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></span>
        </div>
    </form>

</div>

</body>
</html>
