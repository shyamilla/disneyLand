package com.pro;

import java.sql.*;
import java.util.Date;

public class OTPDAO {


    public static OTP getLatestOTP(String email) {
        OTP otpObj = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT otp, expiry FROM users WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                otpObj = new OTP();
                otpObj.setEmail(email);
                otpObj.setOtp(rs.getString("otp"));
                otpObj.setExpiry(rs.getTimestamp("expiry"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return otpObj;
    }

    public static boolean validateOTP(String email, String otp) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM user_otps WHERE email = ? AND otp = ? AND expiry > NOW()";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, otp);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static void deleteOTP(String email) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM user_otps WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.executeUpdate();
    }

    public static void saveOTP(String email, String otp, Timestamp expiry) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO user_otps (email, otp, expiry) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE otp=?, expiry=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, otp);
        ps.setTimestamp(3, expiry);
        ps.setString(4, otp);
        ps.setTimestamp(5, expiry);
        ps.executeUpdate();
    }
}