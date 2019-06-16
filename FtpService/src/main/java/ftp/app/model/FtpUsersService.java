package ftp.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftp.app.dal.FtpUserDao;

@Service
public class FtpUsersService {

	private FtpUserDao ftpUserDao;
	
	@Autowired
	public FtpUsersService(FtpUserDao ftpUserDao) {
		this.ftpUserDao = ftpUserDao;
		
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
		return this.ftpUserDao.save(user);
	}
	
	
	private void setUsersPermissions(FtpUser[] users) {
		final String writePermission = "write"; 
		for (FtpUser user : users) {
			user.setUserPermission(writePermission);
		}
	}
	
	
}
