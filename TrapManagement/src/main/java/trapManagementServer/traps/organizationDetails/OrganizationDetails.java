package trapManagementServer.traps.organizationDetails;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import trapManagementServer.traps.fakeTrapUsers.FakeUser;

@Document(collection="Organization_details")
public class OrganizationDetails {
	
	private String organization;
	private String emailPostfix;
	private List<FakeUser> credentialsForTraps;
	
	public OrganizationDetails() {
		// TODO Auto-generated constructor stub
	}

	@Id
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getEmailPostfix() {
		return emailPostfix;
	}

	public void setEmailPostfix(String emailPostfix) {
		this.emailPostfix = emailPostfix;
	}

	public List<FakeUser> getCredentialsForTraps() {
		return credentialsForTraps;
	}

	public void setCredentialsForTraps(List<FakeUser> credentialsForTraps) {
		this.credentialsForTraps = credentialsForTraps;
	}

}
