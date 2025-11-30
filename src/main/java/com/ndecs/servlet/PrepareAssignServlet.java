package com.ndecs.servlet;

import com.ndecs.dao.TeamDAO;
import com.ndecs.dao.IncidentDAO;
import com.ndecs.model.Team;
import com.ndecs.model.Incident;
import com.ndecs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/PrepareAssignServlet")
public class PrepareAssignServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ADMIN CHECK
        HttpSession session = req.getSession(false);
        if (session == null) { resp.sendRedirect("login.jsp"); return; }
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        try {
            int id = Integer.parseInt(req.getParameter("incidentId"));

            IncidentDAO incDao = new IncidentDAO();
            TeamDAO teamDao = new TeamDAO();

            Incident incident = incDao.getById(id);
            List<Team> teams = teamDao.getAll();   // FIXED HERE

            req.setAttribute("incident", incident);
            req.setAttribute("teams", teams);

            req.getRequestDispatcher("assign_team.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
