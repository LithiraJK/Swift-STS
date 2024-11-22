package lk.ijse.gdse72.swiftsts.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class SendMailUtil {

    public static void sendEmail(String recipient, String subject, String messageBody) {
        // SMTP Server Configuration
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "kariyawasamlithira2@gmail.com"; // Replace with your email
        String password = "tcnl kunb vsaf oazf";   // Replace with your app password

        // SMTP Properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create Session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create Email Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(messageBody);

            // Send Email
            Transport.send(message);
            System.out.println("Email successfully sent to " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email to " + recipient);
        }
    }
}
