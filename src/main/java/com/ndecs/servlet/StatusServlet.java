package com.ndecs.servlet;

import com.ndecs.dao.IncidentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/StatusServlet")
public class StatusServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        String newStatus = "Pending";

        if(action.equals("inprogress"))
            newStatus = "InProgress";

        else if(action.equals("resolved"))
            newStatus = "Resolved";

        else if(action.equals("pending"))
            newStatus = "Pending";

        try {
            IncidentDAO dao = new IncidentDAO();
            dao.updateStatus(id, newStatus);

            resp.sendRedirect("DashboardServlet");

        } catch(Exception e) {
            throw new ServletException(e);
        }
    }
}

