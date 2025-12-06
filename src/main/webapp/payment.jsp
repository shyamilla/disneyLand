<%@ page import="java.math.BigDecimal" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<%@ page session="true" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp?error=session");
        return;
    }

    String razorpayOrderId = request.getParameter("order_id");
    String bookingId = request.getParameter("booking_id");

    // Amount isn't shown here but Razorpay takes it from the backend order
%>
<!DOCTYPE html>
<html>
<head>
    <title>Complete Your Payment</title>
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
</head>
<body>

<h2 style="text-align:center;">Redirecting to Razorpay...</h2>

<form id="payment-response-form" action="<%= request.getContextPath() %>/PaymentSuccessServlet" method="post">

    <input type="hidden" name="razorpay_payment_id" id="razorpay_payment_id">
    <input type="hidden" name="razorpay_order_id" id="razorpay_order_id" value="<%= razorpayOrderId %>">
    <input type="hidden" name="razorpay_signature" id="razorpay_signature">
    <input type="hidden" name="booking_id" value="<%= bookingId %>">
</form>

<script>
    const options = {
        "key": "rzp_test_EUe62RJUHfTuFZ", // Replace with your Razorpay key
        "amount": "", // Not needed here; already defined in the order on backend
        "currency": "USD",
        "name": "Disneyland Tickets",
        "description": "Ticket Booking Payment",
        "order_id": "<%= razorpayOrderId %>",
        "handler": function (response) {
            // Automatically submit to success servlet
            document.getElementById('razorpay_payment_id').value = response.razorpay_payment_id;
            document.getElementById('razorpay_signature').value = response.razorpay_signature;
            document.getElementById('payment-response-form').submit();
        },
        "theme": {
            "color": "#3399cc"
        }
    };

    const rzp = new Razorpay(options);

    // Auto open on page load
    window.onload = function () {
        rzp.open();
    };
</script>
		
</body>
</html>
