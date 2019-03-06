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

import TrapManagementServerTests.ftpTests.FtpUploadDownloadUtilTest;





public class Main {

	public static void main(String[] args) throws FtpException, IOException {
//		System.setProperty("javax.net.debug", "all");
//		
//		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//		UserManager userManager = userManagerFactory.createUserManager();
//
//		
//		
//		
//		BaseUser user = new BaseUser();
//		user.setName("username");
//		user.setPassword("password");
//		user.setHomeDirectory("/tmp");
//		userManager.save(user);
//
//		ListenerFactory listenerFactory = new ListenerFactory();
//		listenerFactory.setPort(2221);
//		
//
//		FtpServerFactory factory = new FtpServerFactory();
//		factory.setUserManager(userManager);
//		factory.addListener("default", listenerFactory.createListener());
//
//		FtpServer server = factory.createServer();
//		server.start();

		
		
		FtpUploadDownloadUtilTest.startFtpServer();
		System.out.println("server is up!");
		System.out.println("Test the server:");
//		listenToFtp();
		
//		FtpUploadDownloadUtilTest.testFtp();
//		FtpUploadDownloadUtilTest.close();

	}
	
	
	
	
	

}
