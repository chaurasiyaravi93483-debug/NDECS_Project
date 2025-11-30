package com.ndecs.servlet;

import com.ndecs.dao.AssignmentDAO;
import com.ndecs.dao.IncidentDAO;
import com.ndecs.model.Assignment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AssignTeamServlet")
public class AssignTeamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int incidentId = Integer.parseInt(req.getParameter("incidentId"));
            int teamId = Integer.parseInt(req.getParameter("teamId"));

            HttpSession session = req.getSession();
            int adminId = (session.getAttribute("userId") != null)
                    ? (int) session.getAttribute("userId")
                    : 0;

            AssignmentDAO assignDao = new AssignmentDAO();
            IncidentDAO incidentDao = new IncidentDAO();

            // Build assignment
            Assignment a = new Assignment();
            a.setIncidentId(incidentId);
            a.setTeamId(teamId);
            a.setAssignedBy(adminId);
            a.setStatus("Assigned");

            boolean ok = assignDao.assignTeam(a);

            if (ok) {
                incidentDao.updateStatus(incidentId, "Assigned");
                resp.sendRedirect("IncidentDetailsServlet?id=" + incidentId + "&assigned=1");
            } else {
                resp.sendRedirect("assign_team.jsp?error=1");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
