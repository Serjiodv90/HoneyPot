package ftp.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftp.app.dal.FtpUserDao;
import ftp.app.logic.HoneyFtpConfigure;

@Service
public class FtpUsersService {

	private FtpUserDao ftpUserDao;
	private HoneyFtpConfigure honeyFtpConfigure;
	
	@Autowired
	public FtpUsersService(FtpUserDao ftpUserDao, HoneyFtpConfigure honeyFtpConfigure) {
		this.ftpUserDao = ftpUserDao;
		this.honeyFtpConfigure = honeyFtpConfigure;
		
	}
	
//	public List<FtpUser> getAllAllowedFtpUsers() {
//		List<FtpUser> users = new ArrayList<>();
//		this.ftpUserDao.findAll().forEach(user->users.add(user));
//		return users;
//	}
	
	public Optional<FtpUser> getUserByName(String name) {
		return this.ftpUserDao.findById(name);
	}
	
	public void saveAllFtpUsers(FtpUser[] users) {
		for (FtpUser user : users) {
			this.saveFtpUser(user);
			System.err.println("FtpUsersService.saveAllFtpUsers()\nUser: " + user);
		}
		
	}
	
	public FtpUser saveFtpUser(FtpUser user) {
		try {
			this.honeyFtpConfigure.addFtpUser(user);
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.ftpUserDao.save(user);
	}
	
	
	private void setUsersPermissions(FtpUser[] users) {
		for (FtpUser user : users) {
			user.setUserPermission(FtpUser.FtpPermission.WRITE);
		}
	}
	
	
}
