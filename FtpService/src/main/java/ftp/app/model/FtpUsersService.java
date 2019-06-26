package ftp.app.model;



import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftp.app.logic.HoneyFtpConfigure;

@Service
public class FtpUsersService {


	private HoneyFtpConfigure honeyFtpConfigure;
	
	@Autowired
	public FtpUsersService( HoneyFtpConfigure honeyFtpConfigure) {
		this.honeyFtpConfigure = honeyFtpConfigure;
		
	}

	
	public void saveAllFtpUsers(FtpUser[] users) {
		for (FtpUser user : users) {
			this.saveFtpUser(user);
			System.err.println("FtpUsersService.saveAllFtpUsers()\nUser: " + user);
		}
		
	}
	
	public void saveFtpUser(FtpUser user) {
		try {
			this.honeyFtpConfigure.addFtpUser(user);
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
