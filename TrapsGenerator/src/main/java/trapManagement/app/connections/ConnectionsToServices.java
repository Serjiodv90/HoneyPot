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
	
	private String hostName;
//	private String ftpHostName;
	
	private String hostPort;
//	private String ftpHostPort;
	
	private String hostPath;
//	private String ftpHostPath;
	
	private String protocol;
	
	private RestTemplate restTemplate;
	
	private Environment env;
	
	@Autowired
	public ConnectionsToServices(Environment env) {
		this.env = env;
	}
	
	
//	@Value("${http.server:localhost}")
//	public void setHttpHostName(String hostName) {
//		this.hostName = hostName;
//	}
//	@Value("${http.port:8092}")
//	public void setHttpHostPort(String hostPort) {
//		this.hostPort = hostPort;
//	}
//	@Value("${http.path:/fakeUsers}")
//	public void setHttpHostPath(String hostPath) {
//		this.hostPath = hostPath;
//	}
//	@Value("${http.protocol:http}")
//	public void setProtocol(String protocol) {
//		this.protocol = protocol;
//	}
	
	@PostConstruct
	public void configRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	private void sendFakeUsers(ArrayList<FakeUser> fakeUsers) {
//		this.protocol = env.getProperty("ftp.protocol");
		System.err.println("ConnectionToServices: sendFakeUsers()");
		FakeUser[] users = new FakeUser[fakeUsers.size()];
		users = fakeUsers.toArray(users);
		
		for(int i=0; i < users.length ; i++) {
			System.out.println(users[i]);
		}
	
		
		String url = //"http://localhost:8085/reports"  ;
				this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		
		System.err.println("URL: " + url );
		this.restTemplate.postForObject(url, users, FakeUser[].class);		
	}


	public void sendFakeUsersToFtp(ArrayList<FakeUser> fakeUsersFtp) {
		this.hostName = env.getProperty("ftp.server");
		this.protocol = env.getProperty("ftp.protocol");
		this.hostPort = env.getProperty("ftp.port");
		this.hostPath = env.getProperty("ftp.path");
		
		sendFakeUsers(fakeUsersFtp);
	}
	
	public void sendFakeUsersToHttp(ArrayList<FakeUser> fakeUsersHttp) {
		this.hostName = env.getProperty("http.server");
		this.protocol = env.getProperty("http.protocol");
		this.hostPort = env.getProperty("http.port");
		this.hostPath = env.getProperty("http.path");
		
		sendFakeUsers(fakeUsersHttp);
	}
	
}
