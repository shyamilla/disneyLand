package com.pro;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class OTPUtil {

    public static void sendOTP(String toEmail, String otp) throws MessagingException {
        final String fromEmail = "shyamilla08@gmail.com"; // ✅ Your Gmail
        final String password = "ymsvvfzkqxxgadxh";        // ✅ App Password (not normal password)

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");  
        props.put("mail.smtp.port", "587");              
        props.put("mail.smtp.auth", "true");             
        props.put("mail.smtp.starttls.enable", "true");  

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        try {
			message.setFrom(new InternetAddress(fromEmail, "Disneyland Support"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Your OTP for Disneyland Password Reset");

        String content = "Dear user,\n\nYour OTP for password reset is: " + otp +
                         "\n\nThis OTP is valid for 5 minutes.\n\nRegards,\nDisneyland Team";

        message.setText(content);
        Transport.send(message);
    }
}
