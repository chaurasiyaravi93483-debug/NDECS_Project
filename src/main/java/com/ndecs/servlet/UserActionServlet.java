package com.ndecs.servlet;

import com.ndecs.dao.UserDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet("/UserActionServlet")
public class UserActionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String action = req.getParameter("action");
            int userId = Integer.parseInt(req.getParameter("userId"));

            UserDAO dao = new UserDAO();

            if ("delete".equals(action)) {
                dao.deleteUser(userId);
            }

            resp.sendRedirect("UserListServlet");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
