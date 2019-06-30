
package userInterfaceService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import userInterfaceService.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    
    Role findByRole(String role);
}
