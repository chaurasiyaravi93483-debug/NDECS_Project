package com.ndecs.servlet;

import com.ndecs.dao.AssignmentDAO;
import com.ndecs.model.Assignment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/AssignmentHistoryServlet")
public class AssignmentHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            AssignmentDAO dao = new AssignmentDAO();
            List<Assignment> list = dao.getAllAssignments(); // Load All Assignments

            req.setAttribute("assignments", list);
            req.getRequestDispatcher("assignment_history.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
