package trapManagementServer.traps.organizationDetails;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trapManagementServer.traps.dal.OrganizationDetailsDao;

@Service
public class OrganizationService {

	private OrganizationDetailsDao oraganizationDao;
	
	@Autowired
	public void setOrganizationDat(OrganizationDetailsDao organizationDao) {
		this.oraganizationDao = organizationDao;
	}
	
	
	public OrganizationDetails createOrganizationUser(OrganizationDetails organizationDetails) {
		return this.oraganizationDao.save(organizationDetails);
	}
	
	
	
	
	
}
