package ftp.app.controller;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftp.app.model.FtpUser;
import ftp.app.model.FtpUsersService;

@RestController
public class FakeUsersRestController {
	
	
	private FtpUsersService ftpuserService;
	
	@Autowired
	public  FakeUsersRestController(FtpUsersService ftpUsersService) {
		this.ftpuserService = ftpUsersService;
	}
	

	@RequestMapping(
			path="/fakeUsers",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	
	public FtpUser[] setUsers(FtpUser[] users) {
		this.ftpuserService.saveAllFtpUsers(users);
		return users;		
	}

}
