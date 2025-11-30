package com.ndecs.dao;

import com.ndecs.model.Assignment;
import com.ndecs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    // =====================================================
    // 1) SAVE NEW ASSIGNMENT
    // =====================================================
    public boolean assignTeam(Assignment a) throws Exception {

        String sql = "INSERT INTO assignments (incident_id, team_id, assigned_by, status) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getIncidentId());
            ps.setInt(2, a.getTeamId());
            ps.setInt(3, a.getAssignedBy());   // INT
            ps.setString(4, a.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    // =====================================================
    // 2) GET ASSIGNMENT HISTORY FOR ONE INCIDENT
    // =====================================================
    public List<Assignment> getAssignmentsByIncident(int incidentId) throws Exception {

        List<Assignment> list = new ArrayList<>();

        String sql =
                "SELECT a.*, t.name AS team_name " +
                "FROM assignments a " +
                "LEFT JOIN teams t ON t.team_id = a.team_id " +
                "WHERE a.incident_id=? ORDER BY a.assigned_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, incidentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapAssignment(rs));
            }
        }

        return list;
    }
    
    public List<Assignment> getAllAssignments() throws SQLException {

        List<Assignment> list = new ArrayList<>();

        String sql = 
            "SELECT a.assign_id, a.incident_id, a.team_id, a.assigned_by, a.assigned_at, " +
            "a.status, t.name AS team_name " +
            "FROM assignments a " +
            "LEFT JOIN teams t ON a.team_id = t.team_id " +
            "ORDER BY a.assigned_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Assignment a = new Assignment();
                a.setAssignId(rs.getInt("assign_id"));
                a.setIncidentId(rs.getInt("incident_id"));
                a.setTeamId(rs.getInt("team_id"));
                a.setAssignedBy(rs.getInt("assigned_by"));
                a.setAssignedAt(rs.getTimestamp("assigned_at"));
                a.setStatus(rs.getString("status"));
                a.setTeamName(rs.getString("team_name"));

                list.add(a);
            }
        }

        return list;
    }


    // =====================================================
    // 3) MAP RESULTSET → Assignment MODEL
    // =====================================================
    private Assignment mapAssignment(ResultSet rs) throws Exception {

        Assignment a = new Assignment();

        a.setAssignId(rs.getInt("assign_id"));
        a.setIncidentId(rs.getInt("incident_id"));
        a.setTeamId(rs.getInt("team_id"));
        a.setAssignedBy(rs.getInt("assigned_by"));  // INT
        a.setAssignedAt(rs.getTimestamp("assigned_at")); // TIMESTAMP
        a.setStatus(rs.getString("status"));
        a.setTeamName(rs.getString("team_name"));

        return a;
    }
}
