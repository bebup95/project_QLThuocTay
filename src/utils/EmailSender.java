/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 *
 * @author nhat
 */
public class EmailSender {
    public static void sendPasswordResetCode(String toEmail, String resetCode) {
        String fromEmail = "your_email@example.com"; // Địa chỉ email của bạn
        String password = "your_email_password";     // Mật khẩu của email
        String host = "smtp.example.com";            // SMTP server (ví dụ: Gmail dùng smtp.gmail.com)

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Mã xác nhận đổi mật khẩu");
            message.setText("Mã xác nhận của bạn là: " + resetCode);

            Transport.send(message); // Gửi email
            System.out.println("Mã reset đã được gửi qua email.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}