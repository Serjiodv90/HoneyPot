package trapManagementServer.traps.fakeTrapUsers;

import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection="Fake_Users")
public class FakeUser {
	
	private String id;
	private String firstName;
	private String lastname;
	private String dedicationServer;
	
	public FakeUser() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDedicationServer() {
		return dedicationServer;
	}

	public void setDedicationServer(String dedicationServer) {
		this.dedicationServer = dedicationServer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
