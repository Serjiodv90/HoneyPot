package trapManagementServer.http.dal;

import org.springframework.data.repository.CrudRepository;

import trapManagementServer.http.model.User;

public interface UserDao extends CrudRepository<User, String>{
	
}
