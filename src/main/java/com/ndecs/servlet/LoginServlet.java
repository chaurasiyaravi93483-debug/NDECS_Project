package com.ndecs.servlet;

import com.ndecs.dao.UserDAO;
import com.ndecs.model.User;
import com.ndecs.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        try {
            UserDAO userDao = new UserDAO();
            User user = userDao.findByEmail(email);

            if (user != null && PasswordUtil.verify(pass, user.getPassword())) {

                // CREATE SESSION
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                // Optional: session expiry time (30 minutes)
                session.setMaxInactiveInterval(30 * 60);

                // REDIRECT AFTER LOGIN
                resp.sendRedirect("DashboardServlet");

            } else {
                req.setAttribute("error", "Invalid email or password");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("login.jsp");
    }
}
