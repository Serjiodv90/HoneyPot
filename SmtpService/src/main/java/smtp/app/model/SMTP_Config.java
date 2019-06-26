package smtp.app.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.server.SMTPServer;


@Component
public class SMTP_Config {


	private SMTPServer smtpServer;
	private int SMTP_Port;
	private String SMTP_Host;
	
	

//	public  SMTP_Config() {
//		this.smtpServer = new SMTPServer(new MessageHandlerFactoryImpl());
//	}
	
	@Autowired
	public  SMTP_Config(SMTPServer smtpServer) {
		System.out.println("SMTP_Config.SMTP_Config()");
		this.smtpServer = smtpServer;
	}
	
	
	@Value("${smtp.host:localhost}")
	public void setSMTP_Host(String smtp_Host) {
		SMTP_Host = smtp_Host;
	}
	
	@Value("${smtp.port:25}")
	public void setSMTP_Port(int smtp_Port) {
		SMTP_Port = smtp_Port;
	}

	
	@PostConstruct
	public void run() {
		this.smtpServer.setHostName(SMTP_Host);
		this.smtpServer.setPort(SMTP_Port);
		this.smtpServer.start();

		System.out.println("TempSmtpConfig.run()\nSmtp started on host: " + this.smtpServer.getHostName() + "\nPort: " + this.smtpServer.getPort());
	}

}
