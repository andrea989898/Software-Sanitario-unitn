/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.softwaresanitario.mailer;
import java.util.Properties;  

import javax.mail.*;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
/**
 *
 * @author franc
 */
public class Mailer {
    public static void send(String to,String subject,String msg){  
  
        final String user="SoftwareSanitario2019@gmail.com";//change accordingly  
        final String pass="Arrapato90";  
  
        //1st step) Get the session object    
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", Integer.toString(465));
        props.setProperty("mail.smtp.socketFactory.port", Integer.toString(465));
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", Boolean.toString(true));
        props.setProperty("mail.smtp.starttls.enable", Boolean.toString(true));
        props.setProperty("mail.debug", Boolean.toString(false));  
  
        Session session = Session.getDefaultInstance(props,  
        new javax.mail.Authenticator() {  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(user,pass);  
            }  
        });  
        //2nd step)compose message  
        try {  
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(user));  
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
            message.setSubject(subject);  
            message.setText(msg);  
   
        //3rd step)send message  
        Transport.send(message);  
  
        System.out.println("Done");  
  
        } catch (MessagingException e) {  
            throw new RuntimeException(e);  
        }  
      
    }  
}
