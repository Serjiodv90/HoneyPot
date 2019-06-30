package trapManagement.app.connections;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import trapManagement.app.model.fakeTrapUsers.FakeUser;

@Component
public class ConnectionsToServices {
	
	private String hostName;
	private String hostPort;
	private String hostPath;
	private String protocol;
	private RestTemplate restTemplate;
	private Environment env;
	
	@Autowired
	public ConnectionsToServices(Environment env) {
		this.env = env;
	}

	@PostConstruct
	public void configRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	private void sendFakeUsers(ArrayList<FakeUser> fakeUsers) {
		FakeUser[] users = new FakeUser[fakeUsers.size()];
		users = fakeUsers.toArray(users);	
		
		String url = 
				this.protocol + 
					 "://" + 
					 this.hostName + 
					 ":" + 
					 this.hostPort +
					 this.hostPath;
		
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
