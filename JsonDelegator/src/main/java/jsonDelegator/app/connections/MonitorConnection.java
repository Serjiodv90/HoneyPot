package jsonDelegator.app.connections;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jsonDelegator.app.model.RequestFormat;

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
	@Value("${monitor.path:/storeReport}")
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	@Value("${monitor.protocol:http}")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@PostConstruct
	public void setRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
		
	public RequestFormat[] sendJsonToMonitor(RequestFormat[] requestArr) {
		String url = this.protocol + "://" + this.hostName + ":" + this.hostPort + this.hostPath;
		return restTemplate.postForObject(url, requestArr, RequestFormat[].class);
	}
	
	

}
