<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Login - NDECS</title></head>
<body style="background:#101010;color:#ffd400;font-family:Arial;">
  <div style="max-width:360px;margin:80px auto;padding:20px;background:#0d0d0d;border:1px solid #222;border-radius:8px;">
    <h2 style="margin:0 0 10px 0;">Login</h2>
    <form action="LoginServlet" method="post">
      <label style="display:block;margin-top:8px;color:#ccc">Email</label>
      <input type="email" name="email" required style="width:100%;padding:8px;border-radius:4px;border:1px solid #333;background:#111;color:#fff"/>
      <label style="display:block;margin-top:8px;color:#ccc">Password</label>
      <input type="password" name="password" required style="width:100%;padding:8px;border-radius:4px;border:1px solid #333;background:#111;color:#fff"/>
      <div style="margin-top:12px;">
        <button type="submit" style="background:#ffd400;color:#000;padding:8px 12px;border:none;border-radius:4px;font-weight:bold;">Login</button>
      </div>
      <div style="color:#f88;margin-top:8px;">
        ${error}
      </div>
    </form>
  </div>
</body>
</html>
