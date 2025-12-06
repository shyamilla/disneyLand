package com.pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DownloadPDF")
public class DownloadPDFServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr == null) {
            response.sendRedirect("profile.jsp?error=missing_booking");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr);
            Booking booking = BookingDAO.getBookingById(bookingId);
            if (booking == null) {
                response.sendRedirect("profile.jsp?error=booking_not_found");
                return;
            }

            // Secure download: only allow the logged-in user to download their own booking
            User user = (User) session.getAttribute("user");
            if (booking.getUserId() != user.getId()) {
                response.sendRedirect("profile.jsp?error=unauthorized_access");
                return;
            }

            // Set headers for downloading PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=booking_" + bookingId + ".pdf");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            PDFGenerator.generateBookingPDF(booking, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("profile.jsp?error=pdf_generation_failed");
        }
    }

    // Optional: forward POST requests to GET
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
