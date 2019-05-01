package trapManagementServer.smtp;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import trapManagementServer.smtp.model.SMTP_Config;


public class SmtpServerConfiguration {
	
public static void main(String[] args) throws InterruptedException {
		
		new SMTP_Config().run();
		Thread.sleep(5000);
		
		
		//test
		System.err.println("After start of smtp");
		
		
		//testing client for smtp via localhost smtp server
		//in case of real smtp server,authentication needed, as registered users and of course knwon host domain
		
		String destmailId = "iliasap4@gmail.com";
		String senderMailId = "serii.d14@gmail.com";
		
		final String userName = "user";	//default james user
		final String pwd = "password";	//default james password
		
		String smtpHost = "localhost";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "25");
		
		//create session object and authenticate userId and password
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, pwd);
			}
		});
		
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
}
