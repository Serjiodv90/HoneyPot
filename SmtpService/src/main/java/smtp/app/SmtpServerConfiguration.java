package smtp.app;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import smtp.app.model.SMTP_Config;


public class SmtpServerConfiguration {
	
	private static Session session;

	public static void main(String[] args) throws InterruptedException {

		new SMTP_Config().run();
		Thread.sleep(5000);


		//test
		System.err.println("After start of smtp");


		//testing client for smtp via localhost smtp server
		//in case of real smtp server,authentication needed, as registered users and of course knwon host domain
		configSession();
		sendTextMail();
		sendMailWithFile();
		
		
	}
	
	public static void configSession() {
		final String userName = "username";	//default james user
		final String pwd = "password";	//default james password

		String smtpHost = "localhost";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "25");

		//create session object and authenticate userId and password
		 session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, pwd);
			}
		});
	}
	
	
	
	public static void sendTextMail() {
		String destmailId = "iliasap4@gmail.com";
		String senderMailId = "serii.d14@gmail.com";

		System.err.println("before try");

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderMailId));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destmailId));
			message.setSubject("Test Mail");
			message.setText("Is it really working?");

			//send the mime message
			Transport.send(message);
			System.out.println("\n\nmail sent!!!\n\n");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	public static void sendMailWithFile() {
		String destmailId = "fileRcpt@gmail.com";
		String senderMailId = "fileTester@gmail.com";
		
		try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(senderMailId));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(destmailId));

	         // Set Subject: header field
	         message.setSubject("Testing Subject");

	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("This is message body");

	         // Create a multipart message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         //Universal-USB-Installer-1.9.8.7.exe
	         String filename = "D:/java/HoneyPot/TrapManagement/target/SMTP_Test/smtpSendFile.txt";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");
	  
	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
		
	}

}
