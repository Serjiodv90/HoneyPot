package ftp.app;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ftp.app.logging.FtpLoggerManager;

@Configuration
public class ConfigurationJsonDelegator {
	
	
	
	@Bean(name="FtpLoggerManager")
	@Scope("prototype")
	public FtpLoggerManager ftpLoggerManager () {
		return new FtpLoggerManager();
	}

	@Bean(name="FtpServerFactory")
	@Scope("singleton")
	public FtpServerFactory ftpServerFactory() {
		return new FtpServerFactory ();
	}
	
	@Bean(name="PropertiesUserManagerFactory")
	@Scope("singleton")
	public PropertiesUserManagerFactory propertiesUserManagerFactory() {
		return new PropertiesUserManagerFactory();
	}
	
}
