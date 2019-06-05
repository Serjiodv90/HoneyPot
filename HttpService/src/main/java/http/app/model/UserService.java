package http.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import http.app.dal.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao dao;
	
	public String login(User user) {
		String username = user.getUserName(); 
		User theUser = dao.findById(username).orElse(null);
		if(theUser != null)
			if(theUser.getPassword().toString().equals(user.getPassword())) {
				return "AfterLogin.jsp";
			}
		
		return "afekaLogin.jsp";
	}
}
