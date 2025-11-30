package com.ndecs.dao;

import com.ndecs.model.Team;
import com.ndecs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    // =====================================================
    // 1) ADD NEW TEAM
    // =====================================================
    public boolean addTeam(Team t) throws Exception {

        String sql = "INSERT INTO teams (name, type, phone, status) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getName());
            ps.setString(2, t.getType());
            ps.setString(3, t.getPhone());
            ps.setString(4, t.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    // =====================================================
    // 2) GET ALL TEAMS
    // =====================================================
    public List<Team> getAll() throws Exception {

        List<Team> list = new ArrayList<>();
        String sql = "SELECT * FROM teams ORDER BY team_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapTeam(rs));
            }
        }
        return list;
    }

    // =====================================================
    // 3) GET TEAM BY ID
    // =====================================================
    public Team getById(int id) throws Exception {

        String sql = "SELECT * FROM teams WHERE team_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapTeam(rs);
        }
        return null;
    }

    // =====================================================
    // 4) UPDATE TEAM
    // =====================================================
    public boolean updateTeam(Team t) throws Exception {

        String sql = "UPDATE teams SET name=?, type=?, phone=?, status=? WHERE team_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getName());
            ps.setString(2, t.getType());
            ps.setString(3, t.getPhone());
            ps.setString(4, t.getStatus());
            ps.setInt(5, t.getTeamId());

            return ps.executeUpdate() > 0;
        }
    }

    // =====================================================
    // 5) DELETE TEAM → SOFT DELETE (Status Offline)
    // =====================================================
    public boolean deleteTeam(int id) throws Exception {

        String sql = "UPDATE teams SET status='Offline' WHERE team_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // =====================================================
    // 6) UPDATE TEAM STATUS ONLY
    // =====================================================
    public boolean updateTeamStatus(int teamId, String newStatus) throws Exception {

        String sql = "UPDATE teams SET status=? WHERE team_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, teamId);

            return ps.executeUpdate() > 0;
        }
    }

    // =====================================================
    // 7) CREATE TEAM (Shortcut for addTeam)
    // =====================================================
    public boolean createTeam(Team t) throws Exception {

    	String sql = "INSERT INTO teams (name, type, phone, status, current_lat, current_lng) VALUES (?, ?, ?, ?, ?, ?)";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getName());
            ps.setString(2, t.getType());
            ps.setString(3, t.getPhone());
            ps.setString(4, t.getStatus());
            ps.setDouble(5, t.getCurrentLat());
            ps.setDouble(6, t.getCurrentLng());


            return ps.executeUpdate() > 0;
        }
    }

    // =====================================================
    // 🎯 MAP RESULTSET INTO TEAM OBJECT
    // =====================================================
    private Team mapTeam(ResultSet rs) throws Exception {

        Team t = new Team();
        t.setTeamId(rs.getInt("team_id"));
        t.setName(rs.getString("name"));
        t.setType(rs.getString("type"));
        t.setPhone(rs.getString("phone"));
        t.setStatus(rs.getString("status"));
        t.setCurrentLat(rs.getDouble("current_lat"));
        t.setCurrentLng(rs.getDouble("current_lng"));

        return t;
    }
}
