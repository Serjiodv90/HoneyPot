package trapManagementServer.smtp.model;

import org.subethamail.smtp.server.SMTPServer;

public class SMTP_Config {

	
private SMTPServer smtpServer;
	
	public  SMTP_Config() {
		this.smtpServer = new SMTPServer(new MessageHandlerFactoryImpl());
	}
	
	public void run() {
		this.smtpServer.setHostName("localhost");
		this.smtpServer.setPort(25);
		this.smtpServer.start();
		
		System.out.println("TempSmtpConfig.run()\nSmtp started on host: " + this.smtpServer.getHostName() + "\nPort: " + this.smtpServer.getPort());
	}
	
}
