package userInterfaceService.connections.monitor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import userInterfaceService.domain.Report;

@Component
public class MonitorConnection {
	
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
	
	public Report[] getAllReports() {
		String url = this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		
		return this.restTemplate.getForObject(url, Report[].class);		
	}
	
	public Report[] getReportsByDateAndType(String fromDate, String type) {
		String url = this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		
		return this.restTemplate.getForObject(url + "?fromDate={fromDate}&type={type}", Report[].class, fromDate,type);
	}
	
	public Report[] getReportsByDate(String fromDate) {
		String url = this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		
		return this.restTemplate.getForObject(url + "?fromDate={fromDate}", Report[].class, fromDate);
	}
	
	public Report[] getReportsByType(String type) {
		String url = this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		
		return this.restTemplate.getForObject(url + "?type={type}", Report[].class, type);
	}
	
	

}
