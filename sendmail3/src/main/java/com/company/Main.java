package com.company;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


//用于方法2
class myAuthenticator extends Authenticator {

    private String uname;
    private String passwd;

    public myAuthenticator(String userName, String passwd) {
        this.uname = userName;
        this.passwd = passwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(uname, passwd);
    }
}

public class Main {
    public static void main(String[] args) {
        // Recipient's email ID needs to be mentioned.
        String to = "test2@163.com    ";

        // Sender's email ID needs to be mentioned
        String from = "test@163.com";

        // Assuming you are sending email from localhost
        String host = "smtp.163.com";

        // Get system properties
        Properties properties = new Properties();
        //System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");


        //方法1
        Session session1 = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("test@163.com", "123456");
            }
        });
        //方法2
        myAuthenticator myay2 = new myAuthenticator("test@163.com", "123456");
        Session session2 = Session.getInstance(properties, myay2);


        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session1);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}