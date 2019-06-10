package userInterfaceService.connections.monitor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import userInterfaceService.domain.OrganizationDetails;

public class monitorConnection {
	
	private String hostName;
	private String hostPort;
	private String hostPath;
	private String protocol;
	private RestTemplate restTemplate;
	
	
	@Value("${monitor.server}:localhost")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	@Value("${monitor.port}:8085")
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}
	@Value("${monitor.path}:/reports")
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	@Value("${monitor.protocol}:https")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@PostConstruct
	public void configRestTemplate() {
		System.out.println("TrapManagementConnection.configRestTemplate()");
		this.restTemplate = new RestTemplate();
	}
	
	public void getReports() {
		System.err.println("TrapManagementConnection.sendOrganizationDetails()");
		getReportsFromMonitor();
	}
	
	private void getReportsFromMonitor() {
		System.err.println("TrapManagementConnection.sendOrganizationDetailsToTrapManagement()");
		String url = "http://localhost:8085/reports"  ;
				//this.protocol + 
//					 "://" + 
//					 this.hostName + 
//					 ":" + 
//					 this.hostPort +
//					 this.hostPath;
//		
		System.err.println("URL: " + url );
		this.restTemplate = new RestTemplate();
		this.restTemplate.postForObject(url, OrganizationDetails.class);		
	}

}
