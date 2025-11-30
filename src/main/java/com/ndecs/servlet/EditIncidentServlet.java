package com.ndecs.servlet;

import com.ndecs.dao.IncidentDAO;
import com.ndecs.model.Incident;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditIncidentServlet")
public class EditIncidentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String idParam = req.getParameter("incidentId");

            if (idParam == null || idParam.trim().length() == 0) {
                resp.sendRedirect("dashboard.jsp?error=invalid_id");
                return;
            }

            int id = Integer.parseInt(idParam);

            IncidentDAO dao = new IncidentDAO();
            Incident inc = dao.getById(id);

            if (inc == null) {
                resp.sendRedirect("dashboard.jsp?error=notfound");
                return;
            }

            // send data to JSP
            req.setAttribute("incident", inc);
            req.getRequestDispatcher("edit_incident.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    // ================================
    //            POST METHOD
    // ================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {

            // SAFETY CHECK
            String idParam = req.getParameter("incidentId");
            if (idParam == null || idParam.trim().isEmpty()) {
                resp.sendRedirect("dashboard.jsp?error=invalid_id");
                return;
            }

            int id = Integer.parseInt(idParam);

            // Create updated incident
            Incident inc = new Incident();
            inc.setIncidentId(id);
            inc.setTitle(req.getParameter("title"));
            inc.setType(req.getParameter("type"));
            inc.setDescription(req.getParameter("description"));
            inc.setLocation(req.getParameter("location"));
            inc.setSeverity(req.getParameter("severity"));
            inc.setStatus(req.getParameter("status"));

            IncidentDAO dao = new IncidentDAO();
            boolean updated = dao.updateIncident(inc);

            if (updated) {
                // FIX: Correct parameter name is id (NOT incidentId)
                resp.sendRedirect("IncidentDetailsServlet?id=" + id + "&updated=1");
            } else {
                resp.sendRedirect("edit_incident.jsp?error=1");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
