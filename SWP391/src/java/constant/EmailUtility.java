/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;

/**
 *
 * @author datkh
 */
public class EmailUtility {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_SENDER = "datkhi2004@gmail.com";
    private static final String APP_PASSWORD = "ivao vomw maku ubbz";

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Tạo số ngẫu nhiên 6 chữ số
        return String.valueOf(otp);
    }

    public static void sendOTP(String recipientEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your OTP Code");

            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; padding: 20px; max-width: 600px;'>"
                    + "<h2 style='color: #333;'>Email Verification</h2>"
                    + "<p>Your OTP code is: <strong style='font-size: 24px; color: #4CAF50;'>%s</strong></p>"
                    + "<p style='color: #666;'>This code will expire in 60 seconds.</p>"
                    + "<p style='color: #999; font-size: 12px;'>If you didn't request this code, please ignore this email.</p>"
                    + "</div>",
                    otp
            );

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage(), e);
        }
    }

    // ham tao mat khau
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;

    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public static void sendTokenMail(String recipientEmail, String token) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Reset Password Request");

            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; padding: 20px; max-width: 600px;'>"
                    + "<h2 style='color: #333;'>Password Reset</h2>"
                    + "<p>Click to this following link to reset your password: http://localhost:9999/SWP391/resetpassword?token=" + token
            );

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage(), e);
        }
    }

    public static void sendReason(String recipientEmail, String reason, String eventName) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Update on Your Event Registration: " + eventName);

            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; padding: 20px; max-width: 600px; border: 1px solid #ddd; border-radius: 10px;'>"
                    + "<h2 style='color: #2E86C1; text-align: center;'>Event Registration Update</h2>"
                    + "<p style='font-size: 16px; color: #333;'>Dear Participant,</p>"
                    + "<p style='font-size: 16px; color: #333;'>We would like to inform you about your registration status for the event <strong>%s</strong>.</p>"
                    + "<div style='background-color: #F8F9F9; padding: 15px; border-left: 5px solid #E74C3C; margin: 15px 0;'>"
                    + "<p style='font-size: 16px; color: #E74C3C;'><strong>Reason:</strong> %s</p>"
                    + "</div>"
                    + "<p style='font-size: 16px; color: #333;'>If you have any questions, please contact us.</p>"
                    + "<p style='font-size: 14px; color: #666;'>Best regards,</p>"
                    + "<p style='font-size: 14px; color: #666;'><strong>Event Management Team</strong></p>"
                    + "</div>",
                    eventName, reason
            );

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send reason email: " + e.getMessage(), e);
        }
    }

    public static void sendSuccessNotification(String recipientEmail, String eventName) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("🎉 Congratulations! Your Registration for " + eventName + " is Confirmed");

            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; padding: 20px; max-width: 600px; border: 1px solid #ddd; border-radius: 10px;'>"
                    + "<h2 style='color: #28a745; text-align: center;'>Registration Confirmed</h2>"
                    + "<p style='font-size: 16px; color: #333;'>Dear Participant,</p>"
                    + "<p style='font-size: 16px; color: #333;'>We are pleased to inform you that your registration for <strong>%s</strong> has been successfully confirmed! 🎉</p>"
                    + "<div style='background-color: #D4EDDA; padding: 15px; border-left: 5px solid #28a745; margin: 15px 0;'>"
                    + "<p style='font-size: 16px; color: #155724;'><strong>Get ready for an amazing experience!</strong></p>"
                    + "</div>"
                    + "<p style='font-size: 16px; color: #333;'>We look forward to seeing you at the event.</p>"
                    + "<p style='font-size: 14px; color: #666;'>Best regards,</p>"
                    + "<p style='font-size: 14px; color: #666;'><strong>Event Management Team</strong></p>"
                    + "</div>",
                    eventName
            );

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send success email: " + e.getMessage(), e);
        }
    }

}
