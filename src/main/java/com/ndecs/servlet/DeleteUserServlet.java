package com.ndecs.servlet;

import com.ndecs.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));

            UserDAO dao = new UserDAO();
            dao.deleteUser(id);

            resp.sendRedirect("UserListServlet");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
