package trapManagementServer.traps.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import trapManagementServer.traps.organizationDetails.OrganizationDetails;
import trapManagementServer.traps.organizationDetails.OrganizationService;

@RestController
public class TrapManagementController {

	private OrganizationService organizationService;
	
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	
	//might be changed
	@RequestMapping(
			path="/organizationDetails",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,	
			consumes=MediaType.APPLICATION_JSON_VALUE
			)
	public OrganizationDetails registerOrganization(@RequestBody OrganizationDetails organizationDetails) {
		System.err.println("\n\nTrapManagementController.registerOrganization()\n" + organizationDetails);
		return this.organizationService.createOrganizationUser(organizationDetails);
	}
}
