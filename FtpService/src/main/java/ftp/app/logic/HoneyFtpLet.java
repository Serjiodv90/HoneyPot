package ftp.app.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ftp.app.ConfigurationJsonDelegator;
import ftp.app.logging.FtpLoggerManager;

public class HoneyFtpLet extends DefaultFtplet {


	private FtpLoggerManager logMan;
	private String userName;
	private String userPassword;
	private List<String> currentConnectedClients = new ArrayList<>();


	public HoneyFtpLet() {
		System.err.println("HoneyFtpLet.HoneyFtpLet()");
	}

	@Override
	public FtpletResult onConnect(FtpSession session) {
		String clientIp = session.getClientAddress().getHostString();

		//if the same client (by its ip address) tries to connect with different userName or something like that, don't create another log fo it
		if(!this.currentConnectedClients.contains(clientIp)) {
			this.currentConnectedClients.add(clientIp);
			ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationJsonDelegator.class);
			logMan = ((FtpLoggerManager)(context.getBean("FtpLoggerManager")));
			logMan.onConnect(clientIp);
		}
		return null;
	}

	@Override
	public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
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

		logMan.onCommand("User's command: " + request.getCommand() + ", " + request.getArgument());
		return null;		
	}

	@Override
	public FtpletResult onDisconnect(FtpSession session) {
		String clientIp = session.getClientAddress().getHostString();

		//make sure that this client didn't disconnected already
		if(this.currentConnectedClients.contains(clientIp)) {
			logMan.onDisconnect(clientIp);
			//delete client from the list when disconnected
			this.currentConnectedClients.remove(clientIp);
		}


		return null;
	}																																																																																


}
