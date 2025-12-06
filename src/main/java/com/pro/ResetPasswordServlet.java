package com.pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("resetEmail") == null) {
            response.sendRedirect("forgotPassword.jsp?error=session_expired");
            return;
        }

        String email = (String) session.getAttribute("resetEmail");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
            return;
        }

        try {
            boolean updated = UserDAO.updatePasswordByEmail(email, password);
            if (updated) {
                session.removeAttribute("resetEmail");
                response.sendRedirect("login.jsp?message=reset_success");
            } else {
                request.setAttribute("error", "Password update failed. Try again.");
                request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Server error. Try again.");
            request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
        }
    }
}
