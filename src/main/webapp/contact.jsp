<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp?error=session");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Contact Us - Disneyland</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/contact.css">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="contact-wrapper">
    <div class="form-contact-container">

        <!-- LEFT: CALL US -->
        <div class="contact-section-left">
            <div class="contact-card">
                <img src="./images/mickymouse.png" alt="Mickey Avatar" class="icon">
                <h2>Call Us</h2>
               <button> 📞 <a href="tel:+1800347639" style="color: black">+1-800-DISNEY</a></button>
            </div>
        </div>

        <!-- CENTER: FORM -->
        <div class="form-section">
            <form action="ContactServlet" method="post">
                <label style="color:white">Need Help? The Magic Team is Here for You!</label>
                <input type="text" name="name" placeholder="Your Name" required>
                <input type="email" name="email" placeholder="Your Email" required>
                <textarea name="message" placeholder="Your Message..." required></textarea>
                <button type="submit" class="submit-btn">Send Message</button>
            </form>

            <%-- ✅ Feedback messages --%>
            <%
                String successMessage = (String) request.getAttribute("successMessage");
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (successMessage != null) {
            %>
                <div class="alert success" id="feedbackMessage"><%= successMessage %></div>
            <% } else if (errorMessage != null) { %>
                <div class="alert error" id="feedbackMessage"><%= errorMessage %></div>
            <% } %>
        </div>

        <!-- RIGHT: EMAIL -->
        <div class="contact-section-right">
            <div class="contact-card">
                <img src="./images/minniemail.png" alt="Minnie Avatar" height="200px" width="150px" class="icon">
                <h2>Email</h2>
              <button>📧 <a href="mailto:support@disneyland.com" style="color:black">support@disneyland.com</a></button>  
            </div>
        </div>

    </div>
</div>

<!-- ✅ Auto-hide feedback message after 5 seconds -->
<script>
    window.addEventListener('DOMContentLoaded', () => {
        const msg = document.getElementById('feedbackMessage');
        if (msg) {
            setTimeout(() => {
                msg.style.display = 'none';
            }, 5000); // 5 seconds
        }
    });
</script>
		
	<%@ include file="chatbot.jsp" %>
</body>
</html>
