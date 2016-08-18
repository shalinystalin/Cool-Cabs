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
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Vivek
 */
public class RegistrationConfirmationMail {

    public void Send(HttpServletRequest request, String accountActivationKey)
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
         InternetAddress.parse(request.getParameter("email")));

         // Set Subject: header field
         message.setSubject("CoolCabs - Thank you for your Resistration");

         //Generate Activation Link
         String accountActivationLink = "http://localhost:8084/CoolCabs/ActivateAccount?username="+request.getParameter("username")+"&key="+accountActivationKey;
         // Now set the actual message
         message.setText("Dear "+request.getParameter("firstname")+",\n\n"
            + "We are happy to inform you that your registration is processed successfully. Below are your account details.\n\n"
            + "Username: "+request.getParameter("username")+"\n"
            + "Password: "+request.getParameter("password")+"\n"
            + "Email: "+request.getParameter("email")+"\n"
            + "Phone: "+request.getParameter("phone")+"\n\n"   
            + "Before you try to login, please activate your account by clicking the below link\n"
            + accountActivationLink+"\n\n"
            + "For registring with us, we are happy to credit $10 dollars to your account. You will receive a confirmation email shortly. \n\n"      
            + "If you have any queries feel free to email us - coolcabsamerica@gmail.com\n\n"
            + "We look forward to see you use our services. Have a good time.");

         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }

    }
    
}
