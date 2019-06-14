package http.app.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import http.app.model.User;

@Repository
public interface UserDao extends MongoRepository<User, String> {
	
	User findByUserName(String userName);
}
