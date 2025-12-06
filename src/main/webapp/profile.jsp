<%@ page import="java.util.*, com.pro.*, java.text.SimpleDateFormat" %>
<%@ page session="true" %>
<%
    Object sessionUser = session.getAttribute("user");
    if (sessionUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String email;
    User user;

    if (sessionUser instanceof String) {
        email = (String) sessionUser;
        UserDAO userDAO = new UserDAO();
        user = userDAO.getUserByEmail(email);
        session.setAttribute("user", user);
    } else {
        user = (User) sessionUser;
    }

    BookingDAO bookingDAO = new BookingDAO();
    List<Booking> bookings = bookingDAO.getBookingsByUserId(user.getId());
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile - Disneyland</title>
    <link rel="stylesheet" href="style.css" />
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #e0eafc, #cfdef3);
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 750px;
            margin: 25px auto;
            background: white;
            border-radius: 15px;
            padding: 30px 40px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        }

        .avatar {
            width: 70px;
            height: 70px;
            background: #4e73df;
            color: white;
            border-radius: 50%;
            font-size: 28px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: auto;
        }

        h2, h3 {
            text-align: center;
            margin-bottom: 15px;
        }

        .info {
            text-align: center;
            margin-bottom: 30px;
        }

        .logout {
            text-align: center;
            padding: 20px;
        }

        .logout a {
            color: white;
            background-color: #dc3545;
            padding: 8px 16px;
            text-decoration: none;
            font-weight: bold;
            border-radius: 6px;
            margin: 5px;
            display: inline-block;
        }

        .logout a:first-child {
            background-color: #6c757d; /* gray for back button */
        }

        form {
            text-align: center;
            margin-bottom: 40px;
        }

        input[type="password"] {
            padding: 8px;
            width: 60%;
            margin: 5px 0;
            border-radius: 6px;
            border: 1px solid #aaa;
        }

        input[type="submit"] {
            padding: 8px 16px;
            border: none;
            border-radius: 6px;
            background-color: #4e73df;
            color: white;
            cursor: pointer;
            font-weight: bold;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        table, th, td {
            border: 1px solid #bbb;
        }

        th, td {
            padding: 10px;
            text-align: center;
        }

        .btn-pdf {
            background-color: #28a745;
            color: white;
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        .booking-scroll {
            max-height: 250px;
            overflow-y: auto;
            margin-top: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .toggle-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 30px;
            gap: 10px;
        }

        .toggle-header {
            flex: 1;
            text-align: center;
            cursor: pointer;
            background-color: #4e73df;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border: 1px solid #ccc;
            transition: background-color 0.3s;
        }

        .toggle-header:hover {
            background-color: #e4e4e4;
            color: black;
        }

        .toggle-section {
            display: none;
        }
    </style>
    <script>
        function toggleSection(id) {
            const sections = ["passwordSection", "bookingSection"];
            sections.forEach(sectionId => {
                const section = document.getElementById(sectionId);
                section.style.display = (sectionId === id && section.style.display !== "block") ? "block" : "none";
            });
        }
    </script>
</head>
<body>

<div class="container">
    <div class="avatar">
        <%= user.getName().substring(0, 1).toUpperCase() %>
    </div>
    <h2><%= user.getName() %></h2>
    <div class="info">
        <strong>Email:</strong> <%= user.getEmail() %>
    </div>

    <div class="toggle-container">
        <h3 class="toggle-header" onclick="toggleSection('passwordSection')">Change Password</h3>
        <h3 class="toggle-header" onclick="toggleSection('bookingSection')">Booking History</h3>
    </div>

    <div id="passwordSection" class="toggle-section">
        <form method="post" action="ResetPasswordServlet">
            <input type="password" name="newPassword" placeholder="Enter new password" required /><br/>
            <input type="submit" value="Update Password" />
        </form>
    </div>

    <div id="bookingSection" class="toggle-section">
        <%
            if (bookings == null || bookings.isEmpty()) {
        %>
            <p style="text-align:center;">No bookings found.</p>
        <%
            } else {
        %>
            <div class="booking-scroll">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Ticket Type</th>
                            <th>Quantity</th>
                            <th>Total Price</th>
                            <th>Visit Date</th>
                            <th>Status</th>
                            <th>PDF</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                            for (Booking b : bookings) {
                        %>
                        <tr>
                            <td><%= b.getId() %></td>
                            <td><%= b.getTicketType() %></td>
                            <td><%= b.getQuantity() %></td>
                            <td>₹<%= b.getTotalPrice() %></td>
                            <td><%= sdf.format(b.getVisitDate()) %></td>
                            <td><%= b.getPaymentStatus() %></td>
                            <td>
                                <form action="DownloadPDFServlet" method="get" target="_blank">
                                    <input type="hidden" name="bookingId" value="<%= b.getId() %>" />
                                    <input type="submit" class="btn-pdf" value="Download" />
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        <%
            }
        %>
    </div>

    <div class="logout">
        <a href="index.jsp">Back</a>
        <a href="LogoutServlet">Logout</a>
    </div>
</div>

<%@ include file="chatbot.jsp" %>
</body>
</html>