<%@ page import="com.pro.Booking" %>
<%@ page session="true" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<%
    Booking booking = (Booking) request.getAttribute("booking");
    if (booking == null) {
        response.sendRedirect("tickets.jsp?error=missing_booking");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Confirmed</title>
    <link rel="stylesheet" href="css/bookingConfirmation.css">
    
</head>
<body>
    <div class="confirmation-container">
        <h1> Booking Confirmed!</h1>

        <p>Thank you, <b><%= booking.getName() %></b>!</p>
        <p>Your <b><%= booking.getTicketType() %></b> ticket(s) for <b><%= booking.getVisitDate() %></b> has been confirmed.</p>
        <p>Booking ID: <%= booking.getId() %></p>
        <p class="total-amount">Total Amount: <%= booking.getTotalPrice()%>$</p>

        <a href="DownloadPDF?bookingId=<%= booking.getId() %>"> Download PDF</a>
        <a href="index.jsp"> Back to Home</a>
    </div>
</body>  
</html>
