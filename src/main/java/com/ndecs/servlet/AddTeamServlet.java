package com.ndecs.servlet;

import com.ndecs.dao.TeamDAO;
import com.ndecs.model.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AddTeamServlet")
public class AddTeamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String name = req.getParameter("name");
            String phone = req.getParameter("phone");
            String type = req.getParameter("type");
            String status = req.getParameter("status");

            Team t = new Team();
            t.setName(name);
            t.setPhone(phone);
            t.setType(type);
            t.setStatus(status);

            // Default geo-location to prevent null error
            t.setCurrentLat(0.0);
            t.setCurrentLng(0.0);

            TeamDAO dao = new TeamDAO();
            boolean ok = dao.createTeam(t);

            if (ok) {
                resp.sendRedirect("team_list.jsp?added=1");
            } else {
                resp.sendRedirect("add_team.jsp?error=1");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
