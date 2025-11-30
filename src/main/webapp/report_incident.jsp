<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Report Incident - NDECS</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <style>
        body{background:#000;color:#ffd400;font-family:Arial;margin:0}
        .wrap{max-width:900px;margin:30px auto;padding:22px;background:#111;border:1px solid #222;border-radius:8px}
        h1{margin:0 0 12px;color:#ffd400}
        label{display:block;margin:8px 0 4px;color:#ddd}
        input,select,textarea{width:100%;padding:10px;border-radius:6px;border:1px solid #333;background:#0f0f0f;color:#ffd400}
        textarea{min-height:120px}
        .row{display:flex;gap:12px;flex-wrap:wrap}
        .col{flex:1;min-width:220px}
        button{background:#ffd400;color:#000;padding:10px 16px;border-radius:8px;border:none;font-weight:bold;cursor:pointer;margin-top:12px}
    </style>
</head>
<body>
<div class="wrap">
    <h1>Report Incident</h1>
    <form method="post" action="IncidentServlet">
        <label>Title</label>
        <input type="text" name="title" required>

        <div class="row">
            <div class="col">
                <label>Type</label>
                <select name="type" required>
                    <option>Fire</option>
                    <option>Crime</option>
                    <option>Medical</option>
                    <option>Accident</option>
                </select>
            </div>

            <div class="col">
                <label>Severity</label>
                <select name="severity" required>
                    <option>Low</option>
                    <option>Medium</option>
                    <option>High</option>
                </select>
            </div>
        </div>

        <label>Description</label>
        <textarea name="description" required></textarea>

        <label>Location</label>
        <input type="text" name="location">

        <button type="submit">Report</button>
    </form>
</div>
</body>
</html>
