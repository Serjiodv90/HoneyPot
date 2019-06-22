package ftp.app.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ftp.app.ConfigurationJsonDelegator;
import ftp.app.logging.FtpLoggerManager;

//http://javadox.com/org.apache.ftpserver/ftplet-api/1.0.5/org/apache/ftpserver/ftplet/package-summary.html

//@Configuration
//@Scope("prototype")
//@Component
public class HoneyFtpLet extends DefaultFtplet {

	
	private FtpLoggerManager logMan;
	private String userName;
	private String userPassword;
	private List<String> currentConnectedClients = new ArrayList<>();
	
//	@Autowired
//	ApplicationContext context;
	
//	@Autowired
//	public void setLogMan(FtpLoggerManager logMan) {
//		this.logMan = logMan;
//	}
	
	public HoneyFtpLet() {
		System.err.println("HoneyFtpLet.HoneyFtpLet()");
	}
	
	@Override
	public FtpletResult onConnect(FtpSession session) {
		System.out.println("\n\nnew Connection: " + session.getClientAddress().getAddress() + ",\t " + session.getClientAddress().getHostString() + "\t "
				+ session.getClientAddress().getAddress() + "\n\n");
		
		String clientIp = session.getClientAddress().getHostString();
				
		//if the same client (by its ip address) tries to connect with different userName or something like that, don't create another log fo it
		if(!this.currentConnectedClients.contains(clientIp)) {
			this.currentConnectedClients.add(clientIp);
			ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationJsonDelegator.class);
			System.err.println(((FtpLoggerManager)context.getBean("FtpLoggerManager")));
//			((FtpLoggerManager)context.getBean("FtpLoggerManager")).delegateJson();
			logMan = ((FtpLoggerManager)(context.getBean("FtpLoggerManager")));//new FtpLoggerManager();
			logMan.onConnect(clientIp);
		}
		return null;
	}
	
	@Override
	 public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
		
		System.out.println("\n\nUser loging in: " + request.getCommand() + ", " + request.getArgument() + "\n\n");
		System.out.println("is user name empty? : " + this.userName );
		if (request.getCommand().equals("PASS") && !this.userName.isEmpty()) {
			this.userPassword = request.getArgument();
			logMan.onLogin(this.userName, this.userPassword);
		}
		
	        return null;
	    }
	
	@Override
	public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException {
		
		if(HoneyFtpCommands.USER.equals(request.getCommand()))
			this.userName = request.getArgument();
		
//		if(request.getCommand().equals("USER"))
			System.out.println("\n\nUser's command: " + request.getCommand() + ", " + request.getArgument() + "\n\n");
		logMan.onCommand("User's command: " + request.getCommand() + ", " + request.getArgument());
		return null;		
	}
	
	@Override
	 public FtpletResult onDisconnect(FtpSession session) {
		System.out.println("\n\nUser dissconnected: \n\n");
		String clientIp = session.getClientAddress().getHostString();
		System.err.println("Current connected users: " + this.currentConnectedClients);

		//make sure that this client didn't disconnected already
		if(this.currentConnectedClients.contains(clientIp)) {
			logMan.onDisconnect(clientIp);
			//delete client from the list when disconnected
			this.currentConnectedClients.remove(clientIp);
		}
		
		
		return null;
	}																																																																																
	
	
}
