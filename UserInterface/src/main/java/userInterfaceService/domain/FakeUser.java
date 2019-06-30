package userInterfaceService.domain;

public class FakeUser {

	private String id;	//generated for DB
	private String firstName; 
	private String lastName;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

@Override
	public String toString() {
		return "First name: " + this.firstName + 
				", Last name: " + this.lastName + "\t";
	}
}
