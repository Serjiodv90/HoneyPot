package userInterfaceService.connections.trapManagement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
	private Environment env;
	
	
	@Value("${trapManagement.server:localhost}")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	@Value("${trapManagement.port:8090}")
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}
	@Value("${trapManagement.path:/organizationDetails}")
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	@Value("${trapManagement.protocol:http}")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Autowired
	public void setEnv(Environment env) {
		this.env = env;
	}
	
	
	@PostConstruct
	public void configRestTemplate() {
		System.out.println("TrapManagementConnection.configRestTemplate()");
		this.restTemplate = new RestTemplate();
	}
	
	
	
	public void sendOrganizationDetailsToTrapManagement(OrganizationDetails details) {
		System.err.println("TrapManagementConnection.sendOrganizationDetailsToTrapManagement()");
		String url = this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		System.err.println("\n\nURL: " + url + "\n\n");
		this.restTemplate.postForObject(url, details, OrganizationDetails.class);		
	}
	
	
	public String getTrapsDownloadPathFromTrapManagement() {
		String url = this.protocol + 
				 	 "://" + 
				 	 this.hostName + 
				 	 ":" + 
				 	 this.hostPort +
				 	 this.env.getProperty("trapManagement.download.path");
		System.err.println("\n\nURL: " + url + "\n\n");
		return this.restTemplate.getForObject(url, String.class);
	}

	
	
	

}
