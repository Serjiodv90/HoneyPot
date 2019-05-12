/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterfaceService.domain;

import java.util.Set;

import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Organization_Users")
public class OrganizationUser {


	private String organization;
	
	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
//	@Pattern(regexp="^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})?$", message="Wrong email")
	private String email;
	private String password;
	
	@DBRef
	private Set<Role> roles;

	
	
	
	@Id
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	@Override
	public String toString() {
		return "Organization User:\nOrganization name: " + this.organization + 
				"\nEmail: " + this.email +
				"\nRoles: " + this.roles;
				
	}

}
