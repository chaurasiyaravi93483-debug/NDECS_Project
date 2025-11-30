package com.ndecs.servlet;

import com.ndecs.dao.UserDAO;
import com.ndecs.model.User;
import com.ndecs.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {

    // Show register form (only for admin)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // admin check
        HttpSession session = req.getSession(false);
        if (session == null) { 
            resp.sendRedirect("login.jsp"); 
            return; 
        }
        User current = (User) session.getAttribute("user");
        if (current == null || !"admin".equalsIgnoreCase(current.getRole())) {
            // non-admin -> show no access page or redirect
            resp.sendRedirect("no_access.jsp");
            return;
        }

        req.getRequestDispatcher("register_user.jsp").forward(req, resp);
    }

    // Handle create user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // admin check (again for POST)
        HttpSession session = req.getSession(false);
        if (session == null) { 
            resp.sendRedirect("login.jsp"); 
            return; 
        }
        User current = (User) session.getAttribute("user");
        if (current == null || !"admin".equalsIgnoreCase(current.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        try {
            // Read + trim inputs
            String name = safeTrim(req.getParameter("name"));
            String email = safeTrim(req.getParameter("email"));
            String password = safeTrim(req.getParameter("password"));
            String role = safeTrim(req.getParameter("role"));

            // basic validation
            if (email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                req.setAttribute("error", "Email, password and role are required.");
                req.getRequestDispatcher("register_user.jsp").forward(req, resp);
                return;
            }

            UserDAO dao = new UserDAO();

            // Check if email already exists
            if (dao.findByEmail(email) != null) {
                req.setAttribute("error", "Email already exists!");
                req.getRequestDispatcher("register_user.jsp").forward(req, resp);
                return;
            }

            // Create User object
            User u = new User();
            u.setName(name);
            u.setEmail(email);
            u.setPassword(PasswordUtil.hash(password)); // currently simple; ok for now
            u.setRole(role);

            boolean saved = dao.createUser(u);   // use createUser defined in UserDAO

            if (saved) {
                req.setAttribute("success", "User created successfully!");
                // Optionally redirect to user list:
                // resp.sendRedirect("UserListServlet");
                // return;
            } else {
                req.setAttribute("error", "Failed to create user. Try again.");
            }

            req.getRequestDispatcher("register_user.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // helper
    private String safeTrim(String s) {
        return (s == null) ? "" : s.trim();
    }
}
