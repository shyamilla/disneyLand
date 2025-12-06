package com.pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/VerifyOtpServlet")
public class VerifyOtpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("otpEmail") == null) {
            response.sendRedirect("forgotPassword.jsp?error=session_expired");
            return;
        }

        String email = (String) session.getAttribute("otpEmail");
        String enteredOtp = request.getParameter("otp");

        try {
            boolean isValid = OTPDAO.validateOTP(email, enteredOtp);

            if (isValid) {
                session.setAttribute("resetEmail", email);  // for ResetPasswordServlet
                session.removeAttribute("otpEmail");        // clean up
                OTPDAO.deleteOTP(email);                    // DB clean up
                response.sendRedirect("resetpassword.jsp");
            } else {
                response.sendRedirect("EnterOtp.jsp?error=invalid_or_expired");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("EnterOtp.jsp?error=invalid_or_expired");
        }
    }
}
