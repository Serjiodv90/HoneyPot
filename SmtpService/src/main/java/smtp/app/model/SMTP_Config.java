package smtp.app.model;

import org.subethamail.smtp.server.SMTPServer;

public class SMTP_Config {


	private SMTPServer smtpServer;
	private int SMTP_Port = 25;
	private String SMTP_Host= "localhost";

	public  SMTP_Config() {
		this.smtpServer = new SMTPServer(new MessageHandlerFactoryImpl());
	}

	public void run() {
		this.smtpServer.setHostName(SMTP_Host);
		this.smtpServer.setPort(SMTP_Port);
		this.smtpServer.start();
		
		
		
		

		System.out.println("TempSmtpConfig.run()\nSmtp started on host: " + this.smtpServer.getHostName() + "\nPort: " + this.smtpServer.getPort());
	}

}
