package smtp.app.connection;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class JsonDelegatorConnection {
	
//	private RestTemplate rest = new RestTemplate();
//	private String url = "http://localhost:8085/storeReport";
//	
//	private ArrayList<RequestFormat> arr = new ArrayList<RequestFormat>();
	
//	public void sendJson(List<RequestFormat> reqArrList) {
//		//arr.add(new RequestFormat("date", "request"));
//		//RequestFormat m = new RequestFormat("date", "request");
//		System.out.println("In try to connect");
//		rest.postForObject(url, reqArrList, ResponseEntity.class);
//	}
	
	
	
	private String hostName;
	private String hostPort;
	private String hostPath;
	private String protocol;
	private RestTemplate restTemplate;
	
	@PostConstruct
	public void setRestTemplate() {
		System.err.println("JsonDelegatorConnection.setRestTemplate()");
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
	
	public String getHostName() {
		return hostName;
	}
	
	
	public RequestFormat[] sendJsonToJsonDelegator(RequestFormat[] requestArr) {
		String url = this.protocol + "://" + this.hostName + ":" + this.hostPort + this.hostPath;
		System.err.println("In try to connect\nURL: " + url);
		System.err.println("\n\nRest ya ben zonaaaa: " + this.restTemplate + "\n\n");
		return restTemplate.postForObject(url, requestArr, RequestFormat[].class);
	}
	

}
