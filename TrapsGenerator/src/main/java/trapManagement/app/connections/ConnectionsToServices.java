package trapManagement.app.connections;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import trapManagement.app.model.fakeTrapUsers.FakeUser;

@Component
public class ConnectionsToServices {
	
	private String httpHostName;
	private String ftpHostName;
	
	private String httpHostPort;
	private String ftpHostPort;
	
	private String httpHostPath;
	private String ftpHostPath;
	
	private String protocol;
	
	private RestTemplate restTemplate;
	
	private Environment env;
	
	@Autowired
	public ConnectionsToServices(Environment env) {
		this.env = env;
	}
	
	
	@Value("${http.server:localhost}")
	public void setHttpHostName(String hostName) {
		this.httpHostName = hostName;
	}
	@Value("${http.port:8080}")
	public void setHttpHostPort(String hostPort) {
		this.httpHostPort = hostPort;
	}
	@Value("${http.path:/fakeUsers}")
	public void setHttpHostPath(String hostPath) {
		this.httpHostPath = hostPath;
	}
	@Value("${http.protocol:http}")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@PostConstruct
	public void configRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	public void sendFakeUsersToHttp(ArrayList<FakeUser> fakeUsersHttp) {
//		this.protocol = env.getProperty("ftp.protocol");
		System.err.println("ConnectionToServices: sendFakeUsersToHttp()");
		fakeUsersHttp.toArray();
		
		String url = //"http://localhost:8085/reports"  ;
				this.protocol + 
					 "://" + 
					 this.httpHostName + 
					 ":" + 
					 this.httpHostPort +
					 this.httpHostPath;
		
		System.err.println("URL: " + url );
		this.restTemplate.postForObject(url, fakeUsersHttp, FakeUser[].class);		
	}

}
