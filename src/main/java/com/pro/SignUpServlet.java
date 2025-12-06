package com.pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email == null || !Pattern.matches(emailRegex, email)) {
            request.setAttribute("error", "Invalid email format.");
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
            return;
        }

        // Password validation (at least 8 characters, one uppercase, one lowercase, one digit)
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (password == null || !Pattern.matches(passwordRegex, password)) {
            request.setAttribute("error", "Password must be at least 8 characters long and include a digit, an uppercase, and a lowercase letter.");
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
            return;
        }

        try {
            boolean success = UserDAO.createUser(name, email, password);
            if (success) {
                response.sendRedirect("login.jsp?message=signup_success");
            } else {
                request.setAttribute("error", "Signup failed. Try again.");
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Server error. Try again.");
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
        }
    }
}
