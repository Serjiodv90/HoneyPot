package http.app.model;

import java.util.Arrays;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import http.app.dal.UserDao;

@Service
public class UserService {

	private UserDao dao;
	private Vector<User> users;
	
	@Autowired
	public UserService(UserDao dao) {
		//this.users = new Vector<User>();
		this.dao = dao;
	}
	
	public String login(User user) {
		String username = user.getUserName(); 
		User theUser = dao.findById(username).orElse(null);
		if(theUser != null)
			if(theUser.getPassword().toString().equals(user.getPassword())) {
				return "AfterLogin.jsp";
			}
		
		return "afekaLogin.jsp";
	}

	public User[] save(User[] users) {
		System.err.println("UserService");
		this.users = new Vector<User>(Arrays.asList(users));
		dao.saveAll(this.users);
		return users;
		
	}

	public User save(User user) {
		return dao.save(user);
	}
}
