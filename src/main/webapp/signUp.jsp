<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/SignUp.css" />
</head>
<body>

    <%@ include file="header.jsp" %>


    <form action="<%= request.getContextPath() %>/SignUpServlet" method="post">
        <h2 class="signup-heading">Sign Up</h2>
        <div class="form-group">
            <input type="text" name="name" id="name" placeholder="Full Name" required />
        </div>
        <div class="form-group">
            <input type="email" name="email" id="email" placeholder="Email" required />
        </div>
        <div class="form-group">
            <input type="tel" name="phone" id="phone" placeholder="Phone Number" pattern="[0-9]{10}" required />
        </div>
        <div class="form-group">
            <input type="password" name="password" id="password" placeholder="Password" required />
        </div>
        <button type="submit">Sign Up</button>
    </form>
	<%@ include file="chatbot.jsp" %>
</body>
</html>
