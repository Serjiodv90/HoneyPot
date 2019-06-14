package trapManagement.app.dal;

import org.springframework.data.repository.CrudRepository;

import trapManagement.app.model.organizationDetails.OrganizationDetails;


public interface OrganizationDetailsDao extends CrudRepository<OrganizationDetails, String> {

}
