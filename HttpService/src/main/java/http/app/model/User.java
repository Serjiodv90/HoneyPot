package http.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	private String userName;
	private String password;
	
	public User() {
		System.err.println("user c'tor");
	}
	
	public User(String userName, String password) {
		System.err.println("User constructor");
		setUserName(userName);
		setPassword(password);
	}
	
	@Id
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		System.out.println("User.setUserName()");
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
//	public int getUid() {
//		return uid;
//	}
//	public void setUid(int uid) {
//		this.uid = uid;
//	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + "]";
	}
	
	
}
