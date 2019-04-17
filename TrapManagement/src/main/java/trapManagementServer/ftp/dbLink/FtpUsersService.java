package trapManagementServer.ftp.dbLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trapManagementServer.ftp.dal.FtpUserDao;

@Service
public class FtpUsersService {

	private FtpUserDao ftpUserDao;
	
	@Autowired
	public FtpUsersService(FtpUserDao ftpUserDao) {
		this.ftpUserDao = ftpUserDao;
	}
	
	public List<FtpUser> getAllAllowedFtpUsers() {
		List<FtpUser> users = new ArrayList<>();
		this.ftpUserDao.findAll().forEach(user->users.add(user));
		return users;
	}
	
	public Optional<FtpUser> getUserByName(String name) {
		return this.ftpUserDao.findById(name);
	}
	
	public FtpUser insertFrpUser(FtpUser user) {
		return this.ftpUserDao.save(user);
	}
	
	
	
}
