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
	private final int MAX_IDLE_TIME = 60000;	// 1 min for session timeout in idle state 

	@Autowired
	public  SMTP_Config(SMTPServer smtpServer) {
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
		this.smtpServer.setConnectionTimeout(this.MAX_IDLE_TIME);
		this.smtpServer.start();
	}

}
