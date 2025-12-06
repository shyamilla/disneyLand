package com.pro;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContactDAO {

    public static boolean saveMessage(ContactMessage contact) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getEmail());
            ps.setString(3, contact.getMessage());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
