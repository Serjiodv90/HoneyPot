package smtp.app.config;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.subethamail.smtp.server.SMTPServer;

import smtp.app.connection.JsonDelegatorConnection;
import smtp.app.model.MessageHandlerFactoryImpl;

@Configuration
public class SmtpConfiguration {
	
	@Bean(name="SMTPServer")
	@Scope("singleton")
	public SMTPServer smtpServer() {
		return new SMTPServer(new MessageHandlerFactoryImpl());
	}
	
//	@Bean(name="JsonDelegatorConnection")
//	@Scope("singleton")
//	public JsonDelegatorConnection jsonDelegatorConnection() {
//		return new JsonDelegatorConnection();
//	}

}
