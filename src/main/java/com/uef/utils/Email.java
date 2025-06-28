package com.uef.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
    private static final Logger logger = LoggerFactory.getLogger(Email.class);

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
        String fromEmail = System.getenv("MAIL_USERNAME") != null ? System.getenv("MAIL_USERNAME") : "epartyuef@gmail.com";
        String password = System.getenv("MAIL_PASSWORD") != null ? System.getenv("MAIL_PASSWORD") : "uyvn ibkq dlos jsnb";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true"); // Enable debug logging for SMTP

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
            msg.setText(message + "\nYour OTP is: " + otp + "\nThis code is valid for 5 minutes.");

            Transport.send(msg);
            logger.info("Email sent successfully to: {}", recipientEmail);
            return new EmailResult(true, "Email sent successfully! Please check " + recipientEmail + " for OTP");
        } catch (MessagingException e) {
            logger.error("Error sending email to {}: {}", recipientEmail, e.getMessage(), e);
            return new EmailResult(false, "Error sending email: " + e.getMessage() + " (Details: " + e.getCause() + ")");
        }
    }
}