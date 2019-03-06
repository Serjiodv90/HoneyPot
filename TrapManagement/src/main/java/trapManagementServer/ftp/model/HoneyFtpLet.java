package trapManagementServer.ftp.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;

import trapManagementServer.ftp.logging.FtpLoggerManager;

//http://javadox.com/org.apache.ftpserver/ftplet-api/1.0.5/org/apache/ftpserver/ftplet/package-summary.html

public class HoneyFtpLet extends DefaultFtplet{

	
	private FtpLoggerManager logMan = new FtpLoggerManager();
	private String userName;
	private String userPassword;
	private List<String> currentConnectedClients = new ArrayList<>();
	
	
	@Override
	public FtpletResult onConnect(FtpSession session) {
		System.out.println("\n\nnew Connection: " + session.getClientAddress().getAddress() + ",\t " + session.getClientAddress().getHostString() + "\t "
				+ session.getClientAddress().getAddress() + "\n\n");
		
		String clientIp = session.getClientAddress().getHostString();
		
		//if the same client (by its ip address) tries to connect with different userName or something like that, don't create another log fo it
		if(!this.currentConnectedClients.contains(clientIp))
			logMan.onConnect(clientIp);
		
		return null;
	}
	
	@Override
	 public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
		
		
		System.out.println("\n\nUser loging in: " + request.getCommand() + ", " + request.getArgument() + "\n\n");
		
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
		
		logMan.onDisconnect(clientIp);
		//delete client from the list when disconnected
		this.currentConnectedClients.remove(clientIp);
		return null;
	}																																																																																
	
	
}
