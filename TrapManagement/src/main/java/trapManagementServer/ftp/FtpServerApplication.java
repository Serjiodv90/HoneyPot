package trapManagementServer.ftp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import trapManagementServer.ftp.model.HoneyFtpUtils;

@SpringBootApplication
public class FtpServerApplication {

	public static void main(String[] args) throws FtpException, IOException {

//		SpringApplication.run(FtpServerApplication.class, args);
		
		HoneyFtpUtils.startFtpServer();
		System.out.println("server is up!");
		System.out.println("Test the server:");

	}
	
	
	
	
	

}
