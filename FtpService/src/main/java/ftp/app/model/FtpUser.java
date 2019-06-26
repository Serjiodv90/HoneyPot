package ftp.app.model;

/*
 * This class represents a single ftp user data.
 */


public class FtpUser {

	private String userName;
	private String password;
	private FtpPermission userPermission;
	
	public enum FtpPermission {WRITE, READ};

	
	public FtpUser() {
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
	
	public void setUserPermission(FtpPermission userPermission) {
		this.userPermission = userPermission;
	}
	
	public FtpPermission getUserPermission() {
		return userPermission;
	}



	@Override
	public String toString() {
		return "FtpUser [userName=" + userName + ", password=" + password + ", userPermission="
				+ userPermission + "]";
	}



}
