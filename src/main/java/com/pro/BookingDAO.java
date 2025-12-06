package com.pro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class BookingDAO {

    public static int insertBooking(Booking booking) {
        int generatedId = -1;
        String sql = "INSERT INTO bookings (user_id, ticket_type, name, email, quantity, total_price, visit_date, razorpay_order_id, payment_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, booking.getUserId());
            ps.setString(2, booking.getTicketType());
            ps.setString(3, booking.getName());
            ps.setString(4, booking.getEmail());
            ps.setInt(5, booking.getQuantity());
            ps.setBigDecimal(6, booking.getTotalPrice());
            ps.setDate(7, new java.sql.Date(booking.getVisitDate().getTime()));
            ps.setString(8, booking.getRazorpayOrderId());
            ps.setString(9, booking.getPaymentStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public static void updatePaymentStatus(String razorpayOrderId, String newStatus) {
        String sql = "UPDATE bookings SET payment_status = ? WHERE razorpay_order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setString(2, razorpayOrderId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Booking getBookingById(int bookingId) {
        Booking booking = null;
        String sql = "SELECT * FROM bookings WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setTicketType(rs.getString("ticket_type"));
                booking.setName(rs.getString("name"));
                booking.setEmail(rs.getString("email"));
                booking.setQuantity(rs.getInt("quantity"));
                booking.setTotalPrice(rs.getBigDecimal("total_price"));
                booking.setVisitDate(rs.getDate("visit_date"));
                booking.setRazorpayOrderId(rs.getString("razorpay_order_id"));
                booking.setPaymentStatus(rs.getString("payment_status"));
                booking.setCreatedAt(rs.getTimestamp("created_at"));
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return booking;
    }
    
    public static List<Booking> getBookingsByUserId(int userId) {
        List<Booking> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bookings WHERE user_id = ? ORDER BY visit_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setTicketType(rs.getString("ticket_type"));
                b.setName(rs.getString("name"));
                b.setEmail(rs.getString("email"));
                b.setQuantity(rs.getInt("quantity"));
                b.setTotalPrice(rs.getBigDecimal("total_price"));
                b.setVisitDate(rs.getDate("visit_date"));
                b.setRazorpayOrderId(rs.getString("razorpay_order_id"));
                b.setPaymentStatus(rs.getString("payment_status"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
