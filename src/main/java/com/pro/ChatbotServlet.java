package com.pro;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/ChatServlet")
public class ChatbotServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userMessage = request.getParameter("message");
        String botResponse = "Sorry, I couldn't understand that.";

        try {
            botResponse = GeminiUtil.askAI(userMessage);  // Call Gemini API
            ChatDAO.saveMessage(userMessage, botResponse); // Store in DB
        } catch (Exception e) {
            botResponse = "⚠️ Error communicating with AI.";
            e.printStackTrace();
        }

        // Escape before sending response to avoid XSS or rendering issues
        String escapedResponse = HTMLUtil.escapeHTML(botResponse);
        response.setContentType("text/plain");
        response.getWriter().write(escapedResponse);
    }
}
