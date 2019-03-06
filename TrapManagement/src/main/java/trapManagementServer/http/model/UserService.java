package trapManagementServer.http.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trapManagementServer.http.dal.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao dao;
	
	public String login(User user) {
		String username = user.getUserName(); 
		User theUser = dao.findById(username).orElse(null);
		if(theUser != null)
			if(theUser.getPassword().toString().equals(user.getPassword())) {
				System.out.println("in if");
				return "AfterLogin.jsp";
			}
		System.out.println("after if");
		return "Home.jsp";
	
	}
}
