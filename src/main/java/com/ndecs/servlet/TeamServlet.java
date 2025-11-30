package com.ndecs.servlet;

import com.ndecs.dao.TeamDAO;
import com.ndecs.model.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/TeamServlet")
public class TeamServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String phone = req.getParameter("phone");
        String status = req.getParameter("status");

        Team t = new Team();
        t.setName(name);
        t.setType(type);
        t.setPhone(phone);
        t.setStatus(status);

        try {
            TeamDAO dao = new TeamDAO();
            dao.addTeam(t);

            resp.sendRedirect("add_team.jsp?success=1");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
