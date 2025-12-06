<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Forgot.css" />
</head>
<body>

    <%@ include file="header.jsp" %>

    <div class="form-wrapper">
        <form action="<%= request.getContextPath() %>/ForgotPasswordServlet" method="post">
            <h2 class="forgot-heading">Forgot Password</h2>
            <div class="form-group">
                <input type="email" name="email" placeholder="Enter your email" required />
            </div>
            <button type="submit">Send OTP</button>

            <%
                String error = request.getParameter("error");
                if ("email_not_found".equals(error)) {
            %>
                <p class="error-message">Email not found!</p>
            <%
                } else if ("server".equals(error)) {
            %>
                <p class="error-message">Server error. Try again.</p>
            <%
                }
            %>
        </form>
    </div>
		
</body>
</html>
