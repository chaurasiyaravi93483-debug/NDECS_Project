package com.ndecs.servlet;

import com.ndecs.dao.IncidentDAO;
import com.ndecs.model.Incident;
import com.ndecs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/AllIncidentsServlet")
public class AllIncidentsServlet extends HttpServlet {

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
            String q = req.getParameter("q");
            String type = req.getParameter("type");
            String severity = req.getParameter("severity");
            String status = req.getParameter("status");
            String from = req.getParameter("from");
            String to = req.getParameter("to");

            int page = 1;
            int pageSize = 10;
            String p = req.getParameter("page");
            if (p != null && !p.isEmpty()) page = Integer.parseInt(p);

            IncidentDAO dao = new IncidentDAO();
            List<Incident> incidents;
            int total;
            boolean hasFilter =
                    (q != null && !q.isEmpty()) ||
                    (type != null && !type.isEmpty()) ||
                    (severity != null && !severity.isEmpty()) ||
                    (status != null && !status.isEmpty()) ||
                    (from != null && !from.isEmpty()) ||
                    (to != null && !to.isEmpty());

            if (hasFilter) {
                incidents = dao.getFiltered(q, type, severity, status, from, to, page, pageSize);
                total = dao.countFiltered(q, type, severity, status, from, to);
            } else {
                incidents = dao.getAll();
                total = incidents.size();
            }

            req.setAttribute("incidents", incidents);
            req.setAttribute("total", total);
            req.setAttribute("page", page);
            req.getRequestDispatcher("all_incidents.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
