<%@ page session="true" %>
<%@ include file="header.jsp" %>
<%
    Object user = session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to Disneyland</title>
	<link rel="stylesheet" type="text/css" href="./css/index.css" />
	
	
</head>
<body>	

<% if (user != null) { %>
    <h1> Welcome Back..</h1>
    <div class="video-section">
        <video autoplay muted loop>
            <source src="videos/afterlogin.mp4" type="video/mp4" />
        </video>
    </div>
<% } else { %>
    <h1> Explore the Magic of Disneyland</h1>
    <div class="video-section">
        <video autoplay muted loop>
            <source src="videos/beforelogin.mp4" type="video/mp4" />
        </video>
    </div>
<% } %>
			<%@ include file="chatbot.jsp" %>
</body>
</html>
