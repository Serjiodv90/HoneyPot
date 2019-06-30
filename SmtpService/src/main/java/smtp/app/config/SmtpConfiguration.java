package smtp.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.subethamail.smtp.server.SMTPServer;

import smtp.app.model.MessageHandlerFactoryImpl;

@Configuration
public class SmtpConfiguration {
	
	@Bean(name="SMTPServer")
	@Scope("singleton")
	public SMTPServer smtpServer() {
		return new SMTPServer(new MessageHandlerFactoryImpl());
	}

}
