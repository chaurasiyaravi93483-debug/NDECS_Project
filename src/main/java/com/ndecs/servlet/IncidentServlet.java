package com.ndecs.servlet;

import com.ndecs.dao.IncidentDAO;
import com.ndecs.model.Incident;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/IncidentServlet")
public class IncidentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Get form fields
            String title = req.getParameter("title");
            String type = req.getParameter("type");
            String desc = req.getParameter("description");
            String location = req.getParameter("location");
            String severity = req.getParameter("severity");

            // Create Incident object
            Incident inc = new Incident();
            inc.setTitle(title);
            inc.setType(type);
            inc.setDescription(desc);
            inc.setLocation(location);
            inc.setSeverity(severity);

            // Save incident
            IncidentDAO dao = new IncidentDAO();
            dao.create(inc);   // ✔ MATCHES YOUR DAO

            // Redirect to dashboard
            resp.sendRedirect("DashboardServlet");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("report_incident.jsp");
    }
}
