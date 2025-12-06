package com.pro;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChatDAO {

    public static void saveMessage(String userMessage, String botResponse) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO chat_messages (user_message, bot_response) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userMessage);
            ps.setString(2, botResponse);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
