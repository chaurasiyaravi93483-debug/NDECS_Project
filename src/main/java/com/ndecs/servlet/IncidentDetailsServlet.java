package com.ndecs.servlet;

import com.ndecs.dao.IncidentDAO;
import com.ndecs.dao.AssignmentDAO;
import com.ndecs.model.Incident;
import com.ndecs.model.Assignment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/IncidentDetailsServlet")
public class IncidentDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String idStr = req.getParameter("id");

            if (idStr == null || idStr.isEmpty()) {
                resp.sendRedirect("DashboardServlet?error=invalid_id");
                return;
            }

            int id = Integer.parseInt(idStr);

            IncidentDAO dao = new IncidentDAO();
            Incident inc = dao.getById(id);

            if (inc == null) {
                resp.sendRedirect("DashboardServlet?error=not_found");
                return;
            }

            AssignmentDAO adao = new AssignmentDAO();
            List<Assignment> history = adao.getAssignmentsByIncident(id);

            req.setAttribute("incident", inc);
            req.setAttribute("history", history);

            req.getRequestDispatcher("incident_details.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
