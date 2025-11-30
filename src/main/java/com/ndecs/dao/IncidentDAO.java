package com.ndecs.dao;

import com.ndecs.model.Incident;
import com.ndecs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidentDAO {

    // =================================================
    // SAFE INCIDENT EXTRACTOR
    // =================================================
    private Incident extractIncident(ResultSet rs) {
        try {
            Incident inc = new Incident();
            inc.setIncidentId(rs.getInt("incident_id"));
            inc.setTitle(rs.getString("title"));
            inc.setType(rs.getString("type"));
            inc.setDescription(rs.getString("description"));
            inc.setLocation(rs.getString("location"));
            inc.setSeverity(rs.getString("severity"));
            inc.setStatus(rs.getString("status"));
            inc.setReportedAt(rs.getTimestamp("reported_at"));
            return inc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // =========================
    // 1) CREATE INCIDENT
    // =========================
    public boolean create(Incident inc) throws SQLException {
        String sql =
                "INSERT INTO incidents (title, type, description, location, severity) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, inc.getTitle());
            ps.setString(2, inc.getType());
            ps.setString(3, inc.getDescription());
            ps.setString(4, inc.getLocation());
            ps.setString(5, inc.getSeverity());

            return ps.executeUpdate() > 0;
        }
    }

    // =========================
    // 2) GET ALL INCIDENTS
    // =========================
    public List<Incident> getAll() throws SQLException {

        List<Incident> list = new ArrayList<>();
        String sql = "SELECT * FROM incidents ORDER BY reported_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Incident inc = extractIncident(rs);
                if (inc != null) list.add(inc);
            }
        }
        return list;
    }

    // =========================
    // 3) GET INCIDENT BY ID
    // =========================
    public Incident getById(int id) throws SQLException {

        String sql = "SELECT * FROM incidents WHERE incident_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return extractIncident(rs);
        }
        return null;
    }

    // =========================
    // 4) UPDATE STATUS
    // =========================
    public boolean updateStatus(int id, String newStatus) throws SQLException {

        String sql = "UPDATE incidents SET status=? WHERE incident_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    // =========================
    // 5) ASSIGN TEAM
    // =========================
    public boolean assignTeam(int incidentId, int teamId) throws SQLException {

        String sql =
                "INSERT INTO assignments (incident_id, team_id, status) VALUES (?, ?, 'Assigned')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, incidentId);
            ps.setInt(2, teamId);

            return ps.executeUpdate() > 0;
        }
    }

    // ===================================================
    // 6) FILTERED INCIDENT LIST (Search + Filters + Pagination)
    // ===================================================
    public List<Incident> getFiltered(
            String q,
            String type,
            String severity,
            String status,
            String fromDate,
            String toDate,
            int page,
            int pageSize
    ) throws SQLException {

        List<Incident> list = new ArrayList<>();

        StringBuilder sql =
                new StringBuilder("SELECT * FROM incidents WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (q != null && !q.isEmpty()) {
            sql.append(" AND (title LIKE ? OR description LIKE ?) ");
            params.add("%" + q + "%");
            params.add("%" + q + "%");
        }

        if (type != null && !type.isEmpty()) {
            sql.append(" AND type=? ");
            params.add(type);
        }

        if (severity != null && !severity.isEmpty()) {
            sql.append(" AND severity=? ");
            params.add(severity);
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND status=? ");
            params.add(status);
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND DATE(reported_at) >= ? ");
            params.add(fromDate);
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND DATE(reported_at) <= ? ");
            params.add(toDate);
        }

        sql.append(" ORDER BY reported_at DESC LIMIT ? OFFSET ? ");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object p : params) ps.setObject(idx++, p);

            ps.setInt(idx++, pageSize);
            ps.setInt(idx, (page - 1) * pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Incident inc = extractIncident(rs);
                if (inc != null) list.add(inc);
            }
        }

        return list;
    }

    // =========================
    // 7) COUNT FILTERED INCIDENTS
    // =========================
    public int countFiltered(
            String q,
            String type,
            String severity,
            String status,
            String fromDate,
            String toDate
    ) throws SQLException {

        StringBuilder sql =
                new StringBuilder("SELECT COUNT(*) AS cnt FROM incidents WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (q != null && !q.isEmpty()) {
            sql.append(" AND (title LIKE ? OR description LIKE ?) ");
            params.add("%" + q + "%");
            params.add("%" + q + "%");
        }

        if (type != null && !type.isEmpty()) {
            sql.append(" AND type=? ");
            params.add(type);
        }

        if (severity != null && !severity.isEmpty()) {
            sql.append(" AND severity=? ");
            params.add(severity);
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND status=? ");
            params.add(status);
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND DATE(reported_at) >= ? ");
            params.add(fromDate);
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND DATE(reported_at) <= ? ");
            params.add(toDate);
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object p : params) ps.setObject(idx++, p);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("cnt");
        }

        return 0;
    }
    
    public boolean updateIncident(Incident inc) throws SQLException {
        String sql = "UPDATE incidents SET title=?, type=?, description=?, location=?, severity=?, status=? WHERE incident_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, inc.getTitle());
            ps.setString(2, inc.getType());
            ps.setString(3, inc.getDescription());
            ps.setString(4, inc.getLocation());
            ps.setString(5, inc.getSeverity());
            ps.setString(6, inc.getStatus());
            ps.setInt(7, inc.getIncidentId());

            return ps.executeUpdate() > 0;
        }
    }
    
    


    // =========================
    // 8) GET RECENT INCIDENTS
    // =========================
    public List<Incident> getRecentIncidents(int limit) throws SQLException {

        List<Incident> list = new ArrayList<>();
        String sql =
                "SELECT * FROM incidents ORDER BY reported_at DESC LIMIT ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Incident inc = extractIncident(rs);
                if (inc != null) list.add(inc);
            }
        }

        return list;
    }
}
