package com.ndecs.servlet;

import com.ndecs.dao.TeamDAO;
import com.ndecs.model.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/UpdateTeamServlet")
public class UpdateTeamServlet extends HttpServlet {

    // =============================
    // 1) UPDATE TEAM (POST)
    // =============================
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("teamId"));
            String name = req.getParameter("name");
            String type = req.getParameter("type");
            String phone = req.getParameter("phone");
            String status = req.getParameter("status");

            Team t = new Team();
            t.setTeamId(id);
            t.setName(name);
            t.setType(type);
            t.setPhone(phone);
            t.setStatus(status);

            TeamDAO dao = new TeamDAO();
            dao.updateTeam(t);

            resp.sendRedirect("team_list.jsp?updated=1");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // =============================
    // 2) DELETE TEAM (GET)
    // =============================
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String action = req.getParameter("action");

            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("teamId"));

                TeamDAO dao = new TeamDAO();
                dao.deleteTeam(id);  // Soft delete = status='Offline'

                resp.sendRedirect("team_list.jsp?deleted=1");
                return;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
