package trapManagement.app.model.organizationDetails;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import trapManagement.app.model.fakeTrapUsers.FakeUser;

@Document(collection="Organization_details")
public class OrganizationDetails {
	
	private String id;
	private String emailPostfix;
	private List<FakeUser> fakeUsers;
	
	public OrganizationDetails() {
	}

	@Id
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmailPostfix() {
		return emailPostfix;
	}

	public void setEmailPostfix(String emailPostfix) {
		this.emailPostfix = emailPostfix;
	}

	public List<FakeUser> getFakeUsers() {
		return fakeUsers;
	}

	public void setFakeUsers(List<FakeUser> fakeUsers) {
		this.fakeUsers = fakeUsers;
	}
	
	@Override
	public String toString() {
		return "Organization's email postfix: " + this.emailPostfix +
				"\nUsers: " + this.fakeUsers;
	}


}
