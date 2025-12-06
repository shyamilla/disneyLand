package com.pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        try {
            UserDAO userDAO = new UserDAO(); // ✅ DAO to fetch user and store OTP
            User user = userDAO.getUserByEmail(email);

            if (user == null) {
                response.sendRedirect("forgotPassword.jsp?error=email_not_found");
                return;
            }

            // Generate 6-digit OTP
            String otp = String.valueOf(100000 + new Random().nextInt(900000));
            Timestamp expiry = new Timestamp(new Date().getTime() + 5 * 60 * 1000); // 5 minutes

            userDAO.storeOTP(email, otp, expiry); // ✅ save OTP in DB

            OTPUtil.sendOTP(email, otp); // ✅ static method to email OTP

            HttpSession session = request.getSession();
            session.setAttribute("otpEmail", email); // used in reset flow

            response.sendRedirect("EnterOtp.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("forgotPassword.jsp?error=server");
        }
    }
}
