package trapManagementServer.traps.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import trapManagementServer.traps.organizationDetails.OrganizationDetails;
import trapManagementServer.traps.organizationDetails.OrganizationUser;

@RepositoryRestController
public interface OrganizationDao extends CrudRepository<OrganizationUser, String> {

}
