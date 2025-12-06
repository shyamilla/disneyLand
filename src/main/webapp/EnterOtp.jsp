<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
    String otpEmail = (String) session.getAttribute("otpEmail");
    if (otpEmail == null) {
        response.sendRedirect("forgotPassword.jsp?error=session_expired");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Enter OTP</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/EnterOtp.css" />
</head>
<body>

    <%@ include file="header.jsp" %>

    <div class="form-container">
        <form action="<%= request.getContextPath() %>/VerifyOtpServlet" method="post">
            <h2 class="otp-heading">Enter OTP</h2>

            <div class="form-group">
                <input type="text" name="otp" placeholder="6-digit OTP" maxlength="6" required />
            </div>

            <button type="submit">Verify</button>

            <% String error = request.getParameter("error");
               if ("invalid_or_expired".equals(error)) { %>
                <p class="error-message">Invalid or expired OTP.</p>
            <% } %>
        </form>
    </div>
	
</body>
</html>
