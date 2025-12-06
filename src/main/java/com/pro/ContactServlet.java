package com.pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp?error=session");
            return;
        }

        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String message = request.getParameter("message");

            if (name != null && email != null && message != null) {
                ContactMessage contact = new ContactMessage(name, email, message);
                boolean saved = ContactDAO.saveMessage(contact);

                if (saved) {
                    request.setAttribute("successMessage", "Your message has been sent successfully!");
                } else {
                    request.setAttribute("errorMessage", "Could not save your message. Please try again.");
                }
            } else {
                request.setAttribute("errorMessage", "Please fill out all fields.");
            }

            request.getRequestDispatcher("contact.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error. Please try again later.");
            request.getRequestDispatcher("contact.jsp").forward(request, response);
        }
    }
}
