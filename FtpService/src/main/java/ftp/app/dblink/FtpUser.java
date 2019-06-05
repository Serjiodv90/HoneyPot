package ftp.app.dblink;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * This class represents a single ftp user data.
 */

@Document(collection="FTP_USERS")
public class FtpUser {
	
	private String userName;
	private String userPwd;
	private String userPermission;

	public FtpUser() {
		
	}

	@Id
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}
	
	
}
