<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
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
    <title>Disneyland Tickets</title>
    <link rel="stylesheet" type="text/css" href="css/ticket.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="ticket-page">
    <h2>Choose Your Magical Experience ✨</h2>

    <div class="ticket-container">
        
        <!-- Regular Ticket -->
        <div class="ticket-card">
            <img src="./images/Regular.jpg" alt="Regular Ticket">
            <h3>Regular Ticket 🎫</h3>
            <p><b>
                The Regular Ticket grants full-day access to Disneyland, including all major rides and shows.
                Ideal for families and everyday visitors, it’s valid on most weekdays and non-peak days—perfect for a magical experience 
                at great value without the extras.</b>
            </p>
            <p class="price">$129 per person</p>
            <a href="bookVisit.jsp?type=regular" class="book-btn">Book Now</a>
        </div>

        <!-- Seasonal Ticket -->
        <div class="ticket-card">
            <img src="./images/Seasonal.jpg" alt="Seasonal Ticket">
            <h3>Seasonal Ticket 🍂</h3>
            <p><b>The Seasonal Ticket is great for visiting during quieter times like spring or fall weekdays.
                 It’s a budget-friendly option with access to most rides and attractions, 
                 ideal for guests who want shorter wait times and a relaxed park vibe.</b>
            </p>
            <p class="price">$149 per person</p>
            <a href="bookVisit.jsp?type=seasonal" class="book-btn">Book Now</a>
        </div>

        <!-- VIP Ticket -->
        <div class="ticket-card">
            <img src="./images/Vip.jpg" alt="VIP Ticket">
            <h3>VIP Ticket 👑</h3>
            <p><b>
               The VIP Ticket offers a luxury experience with skip-the-line access, guided tours, and reserved show seating. 
               It’s ideal for celebrations or anyone seeking premium treatment and exclusive perks throughout their Disneyland adventure.
            </b> </p>
            <p class="price">$299 per person</p>
            <a href="bookVisit.jsp?type=vip" class="book-btn">Book Now</a>
        </div>

    </div>
</div>
		<%@ include file="chatbot.jsp" %>
</body>
</html>
