package http.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import http.app.model.User;
import http.app.model.UserService;

@RestController
public class FakeUsersRestController {
	
	UserService userService;
	
	public FakeUsersRestController() {}
	
	@Autowired
	public FakeUsersRestController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(path="/fakeUsers",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public User[] fakeUsers(@RequestBody User[] users) {

		System.err.println("FakeUsersRestController");
		return userService.save(users);
		 
	}
	
	
}
