package trapManagementServer.http.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import trapManagementServer.http.model.User;

@RepositoryRestResource
public interface UserDao extends CrudRepository<User, String>{
	
}
