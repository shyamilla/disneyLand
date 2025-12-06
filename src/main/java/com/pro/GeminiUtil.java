package com.pro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiUtil {

    private static final String GEMINI_API_KEY = "AIzaSyBPo2c1ZMNEHCGyNcAN6CokLbm0ZWT1h9g"; // Replace with your actual key
    private static final String GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 2000;

    public static String askAI(String userMessage) {
        // Define your system instruction here
        String systemInstructionText = "You are the official Disneyland Bot. Your purpose is to answer questions ONLY about Disneyland parks, attractions, shows, characters, dining, and related services. Do not answer questions that are not directly related to Disneyland. If a question is outside your scope, politely state that you can only answer questions about Disneyland.";

        JSONObject systemInstruction = new JSONObject()
                .put("parts", new JSONArray()
                        .put(new JSONObject().put("text", systemInstructionText)));

        String payload = new JSONObject()
                .put("system_instruction", systemInstruction) // Add the system instruction
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject().put("text", userMessage)))))
                .toString();

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(GEMINI_ENDPOINT + "?key=" + GEMINI_API_KEY).openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.getBytes());
                }

                int status = conn.getResponseCode();
                System.out.println("HTTP Status Code: " + status);

                InputStream is;
                if (status >= 200 && status < 300) {
                    is = conn.getInputStream();
                } else {
                    is = conn.getErrorStream();
                    try (BufferedReader errorBr = new BufferedReader(new InputStreamReader(is))) {
                        StringBuilder errorResponse = new StringBuilder();
                        String errorLine;
                        while ((errorLine = errorBr.readLine()) != null) {
                            errorResponse.append(errorLine);
                        }
                        System.err.println("Gemini API Error Response (Status " + status + "): " + errorResponse.toString());
                    }
                }

                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Gemini Raw Response: " + response.toString());

                    JSONObject jsonResponse = new JSONObject(response.toString());

                    if (jsonResponse.has("error")) {
                        JSONObject errorObject = jsonResponse.getJSONObject("error");
                        String errorMessage = errorObject.optString("message", "Unknown API error.");
                        int errorCode = errorObject.optInt("code", -1);
                        System.err.println("Gemini API Error: Code " + errorCode + ", Message: " + errorMessage);
                        return "Error from AI: " + errorMessage;
                    }

                    JSONArray candidates = jsonResponse.optJSONArray("candidates");
                    if (candidates != null && candidates.length() > 0) {
                        JSONObject content = candidates.getJSONObject(0).optJSONObject("content");
                        if (content != null) {
                            JSONArray parts = content.optJSONArray("parts");
                            if (parts != null && parts.length() > 0) {
                                return parts.getJSONObject(0).optString("text", "No specific text part found.");
                            }
                        }
                    }
                    return "No usable response content from Gemini.";

                }
            } catch (IOException e) {
                System.err.println("Network/Connection error communicating with Gemini API: " + e.getMessage());
                if (attempt == MAX_RETRIES) {
                    return "Gemini AI service is temporarily unavailable due to network issues. Please try again later.";
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ignored) {}
            } catch (org.json.JSONException e) {
                System.err.println("JSON parsing error from Gemini response: " + e.getMessage());
                return "Error processing AI response format.";
            } catch (Exception e) {
                System.err.println("An unexpected error occurred in askAI: " + e.getMessage());
                e.printStackTrace();
                if (attempt == MAX_RETRIES) {
                    return "An unexpected error occurred while communicating with Gemini AI.";
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ignored) {}
            }
        }
        return "Failed to connect to Gemini AI after multiple attempts.";
    }
}