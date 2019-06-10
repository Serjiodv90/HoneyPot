package jsonDelegator.app.connections;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
		System.err.println("JsonDelegatorConnection.setRestTemplate()");
		this.restTemplate = new RestTemplate();
	}
	
	private ArrayList<RequestFormat> arr = new ArrayList<RequestFormat>();
	
	public RequestFormat[] sendJsonToMonitor(RequestFormat[] requestArr) {
		String url = this.protocol + "://" + this.hostName + ":" + this.hostPort + this.hostPath;
		System.err.println("In try to connect\nURL: " + url);
		return restTemplate.postForObject(url, requestArr, RequestFormat[].class);
	}
	
	

}
