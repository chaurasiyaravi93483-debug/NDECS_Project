package com.ndecs.dao;

import com.ndecs.model.User;
import com.ndecs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // CREATE USER
    public boolean createUser(User u) throws Exception {

        String sql = "INSERT INTO users (name, email, password, role, status) VALUES (?, ?, ?, ?, 'Active')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());

            return ps.executeUpdate() > 0;
        }
    }

    // LOGIN
    public User findByEmail(String email) throws Exception {

        String sql = "SELECT * FROM users WHERE email=? LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }
        }
        return null;
    }

    // GET BY ID
    public User getById(int id) throws Exception {

        String sql = "SELECT * FROM users WHERE user_id=? LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }
        }
        return null;
    }

    // GET ALL USERS
    public List<User> getAll() throws Exception {

        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapUser(rs));
            }
        }
        return list;
    }

    // UPDATE USER
    public boolean updateUser(User u) throws Exception {

        String sql = "UPDATE users SET name=?, email=?, password=?, role=? WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());
            ps.setInt(5, u.getUserId());

            return ps.executeUpdate() > 0;
        }
    }

    // SOFT DELETE USER ✅
    public boolean deleteUser(int id) throws Exception {

        String sql = "UPDATE users SET status='Inactive' WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // MAP RESULTSET → USER
    private User mapUser(ResultSet rs) throws Exception {

        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getString("role"));
        u.setStatus(rs.getString("status"));     // ✅ FIX
        u.setCreatedAt(rs.getTimestamp("created_at"));

        return u;
    }

    // UPDATE PASSWORD
    public boolean updatePassword(String email, String newPassword) throws Exception {

        String sql = "UPDATE users SET password=? WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);

            return ps.executeUpdate() > 0;
        }
    }
}
