package com.pro;

import java.sql.*;

import java.util.Date;

public class UserDAO {

	 public User getUserByEmail(String email) throws SQLException {
	        Connection conn = DBConnection.getConnection();
	        String sql = "SELECT * FROM users WHERE email = ?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User user = new User();
	            user.setId(rs.getInt("id"));
	            user.setName(rs.getString("name")); 
	            user.setEmail(rs.getString("email"));
	            user.setPassword(rs.getString("password"));
	            return user;
	        }

	        return null;
	    }

	    public void storeOTP(String email, String otp, Timestamp expiry) throws SQLException {
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
	
    public void registerUser(User user) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getPassword());
        ps.executeUpdate();
        conn.close();
    }

  

    public boolean verifyOTP(String email, String otp) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT otp_expiry FROM users WHERE email = ? AND otp = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, otp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp expiry = rs.getTimestamp("otp_expiry");
                return expiry != null && expiry.after(new Timestamp(new Date().getTime()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   


    public static boolean isValidUser(String email, String password) throws Exception {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, PasswordUtil.hashPassword(password));
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static boolean updatePasswordByEmail(String email, String newPassword) throws Exception {
        Connection conn = DBConnection.getConnection();
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, PasswordUtil.hashPassword(newPassword));
        ps.setString(2, email);
        return ps.executeUpdate() > 0;
    }

    public static boolean createUser(String name, String email, String password) throws Exception {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, PasswordUtil.hashPassword(password));
        return ps.executeUpdate() > 0;
    }
}
