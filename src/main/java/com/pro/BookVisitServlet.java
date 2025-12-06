package com.pro;

import com.razorpay.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

@WebServlet("/BookVisitServlet")
public class BookVisitServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("user");
        int userId;

        try {
            User user = new UserDAO().getUserByEmail(email);
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            userId = user.getId();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=db");
            return;
        }

        try {
            String name = request.getParameter("name");
            String ticketType = request.getParameter("ticketType");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            BigDecimal basePrice = new BigDecimal(request.getParameter("base_price"));
            Date visitDate = Date.valueOf(request.getParameter("visit_date"));

            BigDecimal subtotal = basePrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal gst = subtotal.multiply(BigDecimal.valueOf(0.18));
            BigDecimal totalPrice = subtotal.add(gst);

            // Razorpay expects amount in paise
            Order order = RazorpayUtil.createOrder(totalPrice.multiply(BigDecimal.valueOf(100)));
            String razorpayOrderId = order.get("id");

            Booking booking = new Booking();
            booking.setUserId(userId);
            booking.setTicketType(ticketType);
            booking.setName(name);
            booking.setEmail(email);
            booking.setQuantity(quantity);
            booking.setTotalPrice(totalPrice);
            booking.setVisitDate(visitDate);
            booking.setRazorpayOrderId(razorpayOrderId);
            booking.setPaymentStatus("PENDING");

            int bookingId = BookingDAO.insertBooking(booking);

            response.sendRedirect("payment.jsp?order_id=" + razorpayOrderId + "&booking_id=" + bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("bookVisit.jsp?type=" + request.getParameter("ticketType") + "&error=booking_failed");
        }
    }
}
