package trapManagement.app.model.fakeTrapUsers;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Document(collection="Fake_Users")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class FakeUser {

	private String id;	//generated for DB
	private String firstName; 
	private String lastName;
	
	private String userName;
	private String password;
	private String dedicationServer;


	public FakeUser() {
		
	}
	
	public FakeUser(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getDedicationServer() {
		return dedicationServer;
	}

	public void setDedicationServer(String dedicationServer) {
		this.dedicationServer = dedicationServer;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "FakeUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + ", dedicationServer=" + dedicationServer + "]";
	}






}
