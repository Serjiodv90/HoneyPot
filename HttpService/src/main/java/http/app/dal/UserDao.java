package http.app.dal;

import org.springframework.data.repository.CrudRepository;


import http.app.model.User;


public interface UserDao extends CrudRepository<User, String>{
	
}
