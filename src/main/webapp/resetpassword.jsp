<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reset Password</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Reset.css" />
</head>
<body>

    <%@ include file="header.jsp" %>

    <div class="form-container">
        <form action="<%= request.getContextPath() %>/ResetPasswordServlet" method="post">
            <h2 class="reset-heading">Reset Password</h2>

            <div class="form-group">
                <input type="password" name="password" placeholder="New Password" required />
            </div>
            <div class="form-group">
                <input type="password" name="confirmPassword" placeholder="Confirm Password" required />
            </div>

            <button type="submit">Reset</button>

            <% String error = (String) request.getAttribute("error");
               if (error != null) { %>
                <p class="error-message"><%= error %></p>
            <% } %>
        </form>
    </div>
		
</body>
</html>
