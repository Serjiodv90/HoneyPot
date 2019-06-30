package http.app.model;

import java.util.Arrays;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import http.app.MyUserPrincipal;
import http.app.dal.UserDao;

@Service
public class UserService implements UserDetailsService {

	private UserDao dao;
	private Vector<User> users;

	private BCryptPasswordEncoder encoder;
    
    public UserService() {
    	
    }

	@Autowired
	public UserService(UserDao dao, BCryptPasswordEncoder encoder) {
		this.dao = dao;
		this.encoder = encoder;
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
		this.users = new Vector<User>(Arrays.asList(users));
		for(User u : this.users) {
			save(u);
		}
		return users;

	}

	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return dao.save(user);
	}


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = dao.findByUserName(userName);
		if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + userName);
        }

        return new MyUserPrincipal(user);
	}

	public boolean resetPassword(User user) {
		User newUser = dao.findByUserName(user.getUserName());
		if(newUser != null) {
			newUser.setPassword(user.getPassword());
			save(newUser);
			return true;
		}
		
		else
			return false;
		
	}

}


