package ftp.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * This class represents a single ftp user data.
 */

@Document(collection="FTP_Users")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class FtpUser {

	private String id;
	private String userName;
	private String password;
	private String userPermission;

	
	public FtpUser() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getUserPermission() {
		return userPermission;
	}



	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}



	@Override
	public String toString() {
		return "FtpUser [id=" + id + ", userName=" + userName + ", password=" + password + ", userPermission="
				+ userPermission + "]";
	}



}
