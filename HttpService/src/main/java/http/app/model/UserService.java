package http.app.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
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
		//this.users = new Vector<User>();
		this.dao = dao;
		this.encoder = encoder;
	}

	public String login(User user) {
		String username = user.getUserName(); 
		User theUser = dao.findById(username).orElse(null);
		System.err.println("In Login");
		if(theUser != null)
			if(theUser.getPassword().toString().equals(user.getPassword())) {
				return "AfterLogin.jsp";
			}

		return "afekaLogin.jsp";
	}

	public User[] save(User[] users) {
		System.err.println("UserService");
		this.users = new Vector<User>(Arrays.asList(users));
		for(User u : this.users) {
			save(u);
		}
		//		dao.saveAll(this.users);
		return users;

	}

	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return dao.save(user);
	}


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		System.err.println("loadUserByUsername");
		User user = dao.findByUserName(userName);
		System.out.println("userName= " + userName + "user is " + user);
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


