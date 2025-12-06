<%@ page session="true" %>
<%
    String uri = request.getRequestURI();
    boolean isAttractionsPage = uri.contains("attractions.jsp");
%>
<link rel="stylesheet" type="text/css" href="./css/header.css" />

<nav class="main-header <%= isAttractionsPage ? "scroll-header" : "" %>">
  <a href="index.jsp" style="text-decoration: none; color: inherit;">
    <div class="logo">Disney World</div>
</a>

    <div class="nav-links">
        <a href="index.jsp">Home</a>
        <%
            Object userObj = session.getAttribute("user");
            if (userObj != null) {
        %>
            <a href="attractions.jsp">Attractions</a>
            <a href="tickets.jsp">Tickets</a>
            <a href="contact.jsp">Contact</a>
            <a href="profile.jsp">Profile</a>

        <%
            } else {
        %>
            <a href="signUp.jsp">SignUp</a>
            <a href="login.jsp">Login</a>
        <%
            }
        %>
    </div>
</nav>
