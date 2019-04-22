package trapManagementServer.traps.organizationDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trapManagementServer.traps.dal.OrganizationDao;

@Service
public class OrganizationService {

	private OrganizationDao oraganizationDao;
	
	@Autowired
	public void setOrganizationDat(OrganizationDao organizationDao) {
		this.oraganizationDao = organizationDao;
	}
	
	
	public OrganizationUser createOrganizationUser(OrganizationUser organizationUser) {
		return this.oraganizationDao.save(organizationUser);
	}
	
//	public OrganizationDetails getDetailsByLoginEmail(String LoginEmail) {
//		this.oraganizationDao.fin
//	}
	
	
	
}
