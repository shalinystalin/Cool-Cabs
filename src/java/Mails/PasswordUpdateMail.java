/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mails;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Vivek
 */
public class PasswordUpdateMail {
    
    public void Send(String firstname, String email, String newPassword)
    {
      // Sender's email ID needs to be mentioned
      String from = "coolcabsamerica@gmail";//change accordingly
      final String username = "coolcabsamerica";//change accordingly
      final String password = "Cc123456";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);           
      props.put("mail.smtp.port", "587");

      // Get the Session object.
      Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
         }
      });

      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse(email));

         // Set Subject: header field
         message.setSubject("CoolCabs - Password Update");
         
         // Set Content
         message.setText("Dear "+firstname+",\n\n"
            + "We are writing this to inform about your recent password update.\n\n"
            + "New Password: "+newPassword+"\n\n"      
            + "If you have any queries feel free to email us - coolcabsamerica@gmail.com\n\n");

         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }

    }
}
