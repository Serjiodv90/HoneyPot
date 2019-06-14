package userInterfaceService.domain;

import java.util.ArrayList;
import java.util.List;



public class OrganizationDetails {
	//	private String organization;
	private String emailPostfix;
	private List<FakeUser> fakeUsers;

	
	
	
	public OrganizationDetails() {
		this.fakeUsers = new ArrayList<>(20);
		// TODO Auto-generated constructor stub
	}

	//	@Id
	//	public String getOrganization() {
	//		return organization;
	//	}
	//
	//	public void setOrganization(String organization) {
	//		this.organization = organization;
	//	}

	public List<FakeUser> getFakeUsers() {
		return fakeUsers;
	}

	public void setFakeUsers(List<FakeUser> fakeUsers) {
		this.fakeUsers = fakeUsers;
	}

	public String getEmailPostfix() {
		return emailPostfix;
	}

	public void setEmailPostfix(String emailPostfix) {
		this.emailPostfix = emailPostfix;
	}
	
	public void addFakeUser(FakeUser user) {
		this.fakeUsers.add(user);
	}

	@Override
	public String toString() {
		return "Organization's email postfix: " + this.emailPostfix +
				"\nUsers: " + this.fakeUsers;
	}
}
