package http.app.connections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import http.app.connections.RequestFormat;

@Component
public class JsonDelegatorConnection {
	
	private String hostName;
	private String hostPort;
	private String hostPath;
	private String protocol;
	
	private RestTemplate restTemplate;
	
	@PostConstruct
	public void setRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	@Value("${jsonDelegator.host:localhost}")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	@Value("${jsonDelegator.port:8091}")
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}
	@Value("${jsonDelegator.path:/delegateJson}")
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	@Value("${jsonDelegator.protocol:http}")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public RequestFormat[] sendJsonToJsonDelegator(RequestFormat[] requestArr) {
		String url = this.protocol + "://" + this.hostName + ":" + this.hostPort + this.hostPath;
		return restTemplate.postForObject(url, requestArr, RequestFormat[].class);
	}
	
	

}
