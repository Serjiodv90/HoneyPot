package userInterfaceService.connections.monitor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import userInterfaceService.domain.OrganizationDetails;
import userInterfaceService.domain.Report;

@Component
public class monitorConnection {
	
	private String hostName;
	private String hostPort;
	private String hostPath;
	private String protocol;
	private RestTemplate restTemplate;
	
	
	@Value("${monitor.server:localhost}")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	@Value("${monitor.port:8085}")
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}
	@Value("${monitor.path:/reports}")
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	@Value("${monitor.protocol:http}")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@PostConstruct
	public void configRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	public Report[] getReports() {
		System.err.println("TrapManagementConnection.sendOrganizationDetails()");
		return getReportsFromMonitor();
	}
	
	public Report[] getReportsFromMonitor() {
		System.err.println("TrapManagementConnection.sendOrganizationDetailsToTrapManagement()");
		String url = //"http://localhost:8085/reports"  ;
				this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		
		System.err.println("URL: " + url );
		return this.restTemplate.getForObject(url, Report[].class);		
	}

}
