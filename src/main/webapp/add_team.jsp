<!DOCTYPE html>
<html>
<head>
    <title>Add Team</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">

    <style>
        body{ background:#000; color:#ffd400; font-family:Arial; margin:0; }
        .wrap{ max-width:600px; margin:40px auto; padding:22px;
               background:#111; border:1px solid #222; border-radius:10px; }
        label{ display:block; margin:12px 0 5px; color:#ccc; }
        input,select{
            width:100%; padding:10px; background:#000; border:1px solid #333;
            border-radius:6px; color:#ffd400;
        }
        button{
            padding:12px 16px; background:#ffd400; color:#000;
            font-weight:bold; border:none; border-radius:6px; cursor:pointer;
            margin-top:12px;
        }
    </style>
</head>
<body>

<div class="wrap">
    <h2>Add New Team</h2>

    <form action="AddTeamServlet" method="post">
        <label>Team Name</label>
        <input type="text" name="name" required>

        <label>Team Type</label>
        <input type="text" name="type" required>

        <label>Phone</label>
        <input type="text" name="phone" required>

        <label>Status</label>
        <select name="status">
            <option>Available</option>
            <option>Busy</option>
            <option>Offline</option>
        </select>

        <button type="submit">Add Team</button>
        
    </form>
</div>

</body>
</html>
