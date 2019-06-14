package userInterfaceService.domain;

public class FakeUser {

	private String id;	//generated for DB
	private String firstName; 
	private String lastName;
//	private String password;
//	private String dedicationServer;
	
	public FakeUser() {
		
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

//	public String getDedicationServer() {
//		return dedicationServer;
//	}
//
//	public void setDedicationServer(String dedicationServer) {
//		this.dedicationServer = dedicationServer;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
	
@Override
	public String toString() {
		return "First name: " + this.firstName + 
				", Last name: " + this.lastName + "\t";
	}
}
