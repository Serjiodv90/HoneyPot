package trapManagementServer.traps.organizationDetails;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Organization_Users")
public class OrganizationUser {
	
	private String loginEmail;
	private String password;
	
	@org.springframework.data.mongodb.core.mapping.DBRef(lazy=false)
	private OrganizationDetails details;
	
	public OrganizationUser() {
		// TODO Auto-generated constructor stub
	}

	@Id
	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Reference
	public OrganizationDetails getDetails() {
		return details;
	}

	public void setDetails(OrganizationDetails details) {
		this.details = details;
	}

}
