<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect("index.jsp");
        return;
    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Login.css" />

</head>
<body>

    <%@ include file="header.jsp" %>

    <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
        <h2>Login</h2>
        <input type="text" name="email" placeholder="Email" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Login</button>
        <a href="forgotPassword.jsp">Forgot Password?</a>

        <%
            String error = request.getParameter("error");
            if ("invalid".equals(error)) {
        %>
            <p class="error-message">Invalid email or password.</p>
        <%
            } else if ("server".equals(error)) {
        %>
            <p class="error-message">Server error. Please try again later.</p>
        <%
            } else if ("resetLinkSent".equals(request.getParameter("resetLinkSent"))) {
        %>
            <p class="success-message">Password reset link has been sent to your email.</p>
        <%
            } else if ("reset_success".equals(request.getParameter("message"))) {
        %>
            <p class="success-message">Password reset successful. Please log in.</p>
        <%
            }
        %>
    </form>
		 <%@ include file="chatbot.jsp" %>
</body>
</html>
