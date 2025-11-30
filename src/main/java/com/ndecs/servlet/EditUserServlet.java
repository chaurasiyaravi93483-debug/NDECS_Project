package com.ndecs.servlet;

import com.ndecs.dao.UserDAO;
import com.ndecs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String idStr = req.getParameter("userId");
            if (idStr == null) {
                resp.sendRedirect("UserListServlet");
                return;
            }

            int id = Integer.parseInt(idStr);

            UserDAO dao = new UserDAO();
            User user = dao.getById(id);   // ✅ IMPORTANT

            if (user == null) {
                resp.sendRedirect("UserListServlet?error=notfound");
                return;
            }

            req.setAttribute("user", user);
            req.getRequestDispatcher("edit_user.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            User u = new User();
            u.setUserId(Integer.parseInt(req.getParameter("userId")));
            u.setName(req.getParameter("name"));
            u.setEmail(req.getParameter("email"));
            u.setPassword(req.getParameter("password"));
            u.setRole(req.getParameter("role"));

            UserDAO dao = new UserDAO();
            dao.updateUser(u);

            resp.sendRedirect("UserListServlet?updated=1");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
