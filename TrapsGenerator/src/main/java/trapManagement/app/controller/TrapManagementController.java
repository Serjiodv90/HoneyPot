package trapManagement.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import trapManagement.app.model.organizationDetails.OrganizationDetails;
import trapManagement.app.model.organizationDetails.OrganizationService;

@RestController
public class TrapManagementController {

	
	private OrganizationService organizationService;
	
	@Autowired
	public TrapManagementController(OrganizationService organizationService) {
		this.organizationService = organizationService; 
		System.err.println("kukukaka");
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
