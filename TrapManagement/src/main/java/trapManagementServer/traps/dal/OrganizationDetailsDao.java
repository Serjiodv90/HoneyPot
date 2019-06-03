package trapManagementServer.traps.dal;

import org.springframework.data.repository.CrudRepository;
import trapManagementServer.traps.organizationDetails.OrganizationDetails;


public interface OrganizationDetailsDao extends CrudRepository<OrganizationDetails, String> {

}
