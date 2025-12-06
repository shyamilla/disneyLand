<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp?error=session");
        return;
    }

    String ticketType = request.getParameter("type");
    int basePrice = 0;
    int ticketTypeId = 0;
    String displayType = "TICKET";

    if ("regular".equalsIgnoreCase(ticketType)) {
        basePrice = 129;
        ticketTypeId = 1;
        displayType = "REGULAR";
    } else if ("seasonal".equalsIgnoreCase(ticketType)) {
        basePrice = 149;
        ticketTypeId = 2;
        displayType = "SEASONAL";
    } else if ("vip".equalsIgnoreCase(ticketType)) {
        basePrice = 299;
        ticketTypeId = 3;
        displayType = "VIP";
    }

    String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
%>
<!DOCTYPE html>
<html>
<head>
    <title>Book Visit - <%= displayType %></title>
    <link rel="stylesheet" href="css/book.css">
    <script>
        function calculateTotal() {
            const qty = parseInt(document.getElementById("quantity").value) || 0;
            const basePriceUSD = parseFloat(document.getElementById("basePrice").value);
            const gstRate = 0.18;

            const subtotalUSD = qty * basePriceUSD;
            const gstAmount = subtotalUSD * gstRate;
            const finalAmountUSD = subtotalUSD + gstAmount;

            document.getElementById("totalAmount").innerText = "$" + finalAmountUSD.toFixed(2) + " (incl. 18% GST)";
            document.getElementById("finalAmountUSD").value = finalAmountUSD.toFixed(2);
        }
        window.onload = calculateTotal;
    </script>
</head>
<body>

<%@ include file="header.jsp" %>

<div class="booking-form">
    <h2>Book Your Visit - <%= displayType %></h2>

    <% 
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p class="error-message"><%= error %></p>
    <% } %>

    <form action="BookVisitServlet" method="post">
        <input type="hidden" name="ticket_type_id" value="<%= ticketTypeId %>">
        <input type="hidden" id="basePrice" name="base_price" value="<%= basePrice %>">
        <input type="hidden" name="ticketType" value="<%= ticketType %>">
        <input type="hidden" id="finalAmountUSD" name="total_price" value="">

        <label>Your Name:</label><br>
        <input type="text" name="name" placeholder="Your Name" required><br><br>

        <label>Your Email:</label><br>
        <input type="email" name="email" placeholder="Your Email" required><br><br>

        <label>Visit Date:</label><br>
        <input type="date" name="visit_date" min="<%= today %>" required><br><br>

        <label>Number of Tickets:</label><br>
        <input type="tel" id="quantity" name="quantity" min="1" value="1" onchange="calculateTotal()" required><br><br>

        <p><strong>Total Amount:</strong> <span id="totalAmount">$0.00</span></p>

        <button type="submit">Book Now</button>
    </form>
</div>
		<%@ include file="chatbot.jsp" %>
</body>
</html>
