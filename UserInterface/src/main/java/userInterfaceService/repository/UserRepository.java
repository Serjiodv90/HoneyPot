
package userInterfaceService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import userInterfaceService.domain.OrganizationUser;

public interface UserRepository extends MongoRepository<OrganizationUser, String> {
    
    OrganizationUser findByEmail(String email);
    OrganizationUser findByOrganization(String organization);
    
}
