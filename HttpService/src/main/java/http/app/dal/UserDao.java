package http.app.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import http.app.model.User;

@Repository
public interface UserDao extends CrudRepository<User, String>{
	
}
