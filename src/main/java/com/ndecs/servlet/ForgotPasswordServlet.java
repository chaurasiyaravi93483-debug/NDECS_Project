package com.ndecs.servlet;

import com.ndecs.dao.UserDAO;
import com.ndecs.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String email = req.getParameter("email");

            UserDAO dao = new UserDAO();
            User user = dao.findByEmail(email);

            if (user == null) {
                req.setAttribute("error", "Email not found!");
                req.getRequestDispatcher("forgot_password.jsp").forward(req, resp);
                return;
            }

            // Email exists -> go to reset page
            req.setAttribute("email", email);
            req.getRequestDispatcher("reset_password.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

