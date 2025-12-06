package com.pro;

import java.sql.Timestamp;

public class ChatMessage {
    private int id;
    private String userMessage;
    private String botResponse;
    private Timestamp createdAt;

    // Getters and Setters
    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }

    public String getBotResponse() { return botResponse; }
    public void setBotResponse(String botResponse) { this.botResponse = botResponse; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
