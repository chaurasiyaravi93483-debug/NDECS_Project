package com.ndecs.servlet;

import com.ndecs.dao.UserDAO;
import com.ndecs.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String email = req.getParameter("email");
            String newPass = req.getParameter("password");

            UserDAO dao = new UserDAO();
            boolean updated = dao.updatePassword(email, PasswordUtil.hash(newPass));

            if (updated) {
                req.setAttribute("success", "Password updated successfully!");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Failed to update password!");
                req.getRequestDispatcher("reset_password.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
