package com.pro;

import com.razorpay.Utils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/PaymentSuccessServlet")
public class PaymentSuccessServlet extends HttpServlet {

    // Handle POST requests from Razorpay
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("PaymentSuccessServlet: Invoked");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp?error=session");
            return;
        }

        String razorpayPaymentId = request.getParameter("razorpay_payment_id");
        String razorpayOrderId = request.getParameter("razorpay_order_id");
        String razorpaySignature = request.getParameter("razorpay_signature");
        String bookingIdStr = request.getParameter("booking_id");

        if (razorpayPaymentId == null || razorpayOrderId == null || razorpaySignature == null || bookingIdStr == null) {
            response.sendRedirect("index.jsp?error=invalid_payment_data");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr);

            // Verify signature
            Map<String, String> params = new HashMap<>();
            params.put("razorpay_order_id", razorpayOrderId);
            params.put("razorpay_payment_id", razorpayPaymentId);

            // Uncomment for real signature verification
            // boolean isValid = Utils.verifyPaymentSignature(params, razorpaySignature, RazorpayUtil.RAZORPAY_SECRET);
            boolean isValid = true;

            if (!isValid) {
                response.sendRedirect("index.jsp?error=invalid_signature");
                return;
            }

            // Update payment status in DB
            BookingDAO.updatePaymentStatus(razorpayOrderId, "PAID");

            // Fetch booking to display
            Booking booking = BookingDAO.getBookingById(bookingId);
            if (booking == null) {
                response.sendRedirect("index.jsp?error=booking_not_found");
                return;
            }

            // Show success page
            request.setAttribute("booking", booking);
            request.getRequestDispatcher("BookingConfirmation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=server");
        }
    }

    // Handle GET requests for ping
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().write("Ping: PaymentSuccessServlet is reachable");
    }
}
