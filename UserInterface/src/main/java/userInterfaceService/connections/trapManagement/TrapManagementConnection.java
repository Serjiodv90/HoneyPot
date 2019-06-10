package userInterfaceService.connections.trapManagement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import userInterfaceService.domain.OrganizationDetails;

@Component
public class TrapManagementConnection {
	
	
	private String hostName;
	private String hostPort;
	private String hostPath;
	private String protocol;
	private RestTemplate restTemplate;
	
	
	@Value("${trapManagement.server}:localhost")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	@Value("${trapManagement.port}:8090")
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}
	@Value("${trapManagement.path}:/organizationDetails")
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	@Value("${trapManagement.protocol}:https")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@PostConstruct
	public void configRestTemplate() {
		System.out.println("TrapManagementConnection.configRestTemplate()");
		this.restTemplate = new RestTemplate();
	}
	
	public void sendOrganizationDetails(OrganizationDetails details) {
		System.err.println("TrapManagementConnection.sendOrganizationDetails()");
		sendOrganizationDetailsToTrapManagement(details);
	}
	
	private void sendOrganizationDetailsToTrapManagement(OrganizationDetails details) {
		System.err.println("TrapManagementConnection.sendOrganizationDetailsToTrapManagement()");
		String url = "http://localhost:8090/organizationDetails"  ;
				//this.protocol + 
//					 "://" + 
//					 this.hostName + 
//					 ":" + 
//					 this.hostPort +
//					 this.hostPath;
//		
		System.err.println("URL: " + url + "\nDETAILS: " + details);
		this.restTemplate = new RestTemplate();
		this.restTemplate.postForObject(url, details, OrganizationDetails.class);		
	}

	
	
	

}
