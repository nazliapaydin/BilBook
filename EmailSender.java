import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * A class to contains the method to send an email.
 * Author: Kerem Demirören
 */
public class EmailSender {

   public static void sendVerificationCode(String recipientAdress, String verificationCode) {

      // Recipient's email ID needs to be mentioned.
      String to = recipientAdress;

      // Sender's email ID needs to be mentioned
      String from = "bilbookteam@gmail.com";

      // Assuming you are sending email from through gmails smtp
      String host = "smtp.gmail.com";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      
      // Get the Session object.// and pass username and password
      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

          protected PasswordAuthentication getPasswordAuthentication() {

              return new PasswordAuthentication(from, "wkmsufkctolfbusw");

          }

      });

      // Used to debug SMTP issues
      session.setDebug(true);

      try {
          // Create a default MimeMessage object.
          MimeMessage message = new MimeMessage(session);

          // Set From: header field of the header.
          message.setFrom(new InternetAddress(from));

          // Set To: header field of the header.
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

          // Set Subject: header field
          message.setSubject("A message from the BilTeam!!!");

          // Now set the actual message
          message.setText("Here is your code -> " + verificationCode + "\n\nThanks for using BilBook <3");

          System.out.println("sending...");
          // Send message
          Transport.send(message);
          System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
          mex.printStackTrace();
      }
   }

   public static void sendNotification(String recipientAdress, String text) {

    // Recipient's email ID needs to be mentioned.
    String to = recipientAdress;

    // Sender's email ID needs to be mentioned
    String from = "bilbookteam@gmail.com";

    // Assuming you are sending email from through gmails smtp
    String host = "smtp.gmail.com";

    // Get system properties
    Properties properties = System.getProperties();

    // Setup mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    
    // Get the Session object.// and pass username and password
    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(from, "wkmsufkctolfbusw");

        }

    });

    // Used to debug SMTP issues
    session.setDebug(true);

    try {
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject("A message from the BilTeam!!!");

        // Now set the actual message
        message.setText(text + "\n\nThanks for using BilBook <3");

        System.out.println("sending...");
        // Send message
        Transport.send(message);
        System.out.println("Sent message successfully....");
    } catch (MessagingException mex) {
        mex.printStackTrace();
    }
 }
}