package ftp.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ftp.app.connections.JsonDelegatorConnection;
import ftp.app.logging.FtpLoggerManager;

@Configuration
public class ConfigurationJsonDelegator {

	
	
	@Bean(name="FtpLoggerManager")
	@Scope("prototype")
	public FtpLoggerManager ftpLoggerManager () {
		return new FtpLoggerManager();
	}
	
	@Bean(name="JsonDelegatorConnection")
	@Scope("singleton")
	public JsonDelegatorConnection jsonDelegatorConnection () {
		return new JsonDelegatorConnection();
	}
	
	
}
