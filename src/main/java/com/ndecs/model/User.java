package com.ndecs.model;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String status;     // ✅ ADDED
    private Timestamp createdAt;

    public int getUserId() { return userId; }
    public void setUserId(int id) { this.userId = id; }

    public String getName() { return name; }
    public void setName(String n) { this.name = n; }

    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }

    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }

    public String getRole() { return role; }
    public void setRole(String r) { this.role = r; }

    public String getStatus() { return status; }       // ✅ ADDED
    public void setStatus(String s) { this.status = s; } // ✅ ADDED

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp t) { this.createdAt = t; }
}
