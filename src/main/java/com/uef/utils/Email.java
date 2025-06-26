/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.utils;

/**
 *
 * @author Administrator
 */
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class Email {
    public static class EmailResult {
        private boolean success;
        private String message;

        public EmailResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    public static EmailResult sendEmail(String subject, String recipientEmail, String message, String otp) {
        // Cấu hình Jakarta Mail
        String fromEmail = "epartyuef@gmail.com";
        String password = "uyvn ibkq dlos jsnb";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session mailSession = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            msg.setSubject(subject);
            
            msg.setText(message);

            Transport.send(msg);
            return new EmailResult(true, "Email sent successfully! Please check " + recipientEmail + " for OTP");
        } catch (MessagingException e) {
            return new EmailResult(false, "Error sending email: " + e.getMessage());
        }
    }
}

