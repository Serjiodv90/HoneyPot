package ftp.app.logic;

import java.io.IOException;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;

import java.io.*;
import java.util.*;
import org.apache.ftpserver.*;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.*;
import org.apache.ftpserver.usermanager.impl.*;
import org.springframework.data.domain.ExampleMatcher.NullHandler;

import ftp.app.model.FtpUser;

public class HoneyFtpConfigure {


	/**
	 * Create FTP server.
	 * @param ftpPort FTP port, eg 2121
	 * @param ftpHomeDir FTP directory, eg "target / FtpHome"
	 * @param readUserName read-only user: name
	 * @param readUserPwd read-only user: password
	 * @param writeUserName user with write access: name
	 * @param writeUserPwd user with write access: password
	 * @param ftpUsersPropsFile can be null, or eg "target / FtpUsers.properties"
	 * @param maxLogins maximum number of logins (0 for default value)
	 */ 
	
//	private static Listener listener;
	
	
	private static FtpServerFactory serverFactory;
	private static PropertiesUserManagerFactory userManagerFactory;


	public static FtpServer createFtpServer (int ftpPort, String ftpHomeDir,
			String readUserName, String readUserPwd, String writeUserName, String writeUserPwd,
			String ftpUsersPropsFile, int maxLogins, int maxIdleTimeSec) throws FtpException, IOException
	{
		
		File fhd = new File (ftpHomeDir);
		if (!fhd.exists ())
			fhd.mkdirs ();

		ListenerFactory listenerFactory = new ListenerFactory ();
		listenerFactory.setPort (ftpPort);
		

		userManagerFactory = new PropertiesUserManagerFactory ();
		userManagerFactory.setPasswordEncryptor (new SaltedPasswordEncryptor ());
		if (ftpUsersPropsFile != null && ftpUsersPropsFile.trim (). length ()> 0) {
			File upf = new File (ftpUsersPropsFile);
			if (!upf.exists ()) 
				upf.createNewFile ();
			userManagerFactory.setFile(upf);
		}
//
//		// Create a read-only user and a user with write permission:
//		UserManager userManager = userManagerFactory.createUserManager ();
//		BaseUser userRd = new BaseUser ();
//		userRd.setName (readUserName);
//		userRd.setPassword (readUserPwd);
//		userRd.setHomeDirectory (ftpHomeDir);
//		
//		BaseUser userWr = new BaseUser ();
//		userWr.setName (writeUserName);
//		userWr.setPassword (writeUserPwd);
//		userWr.setHomeDirectory (ftpHomeDir);
//		
//		if (maxIdleTimeSec > 0) {
//			userRd.setMaxIdleTime (maxIdleTimeSec);
//			userWr.setMaxIdleTime (maxIdleTimeSec);
//		}
		
//		List <Authority> authorities = new ArrayList <Authority> ();
//		authorities.add (new WritePermission ());
//		userWr.setAuthorities (authorities);
//		
//		userManager.save (userRd);
//		userManager.save (userWr);
//
		serverFactory = new FtpServerFactory ();
//		listener = listenerFactory.createListener ();
//		serverFactory.addListener ("default", listener);
//		serverFactory.setUserManager (userManager);
		
		
		if (maxLogins > 0) {
			ConnectionConfigFactory ccf = new ConnectionConfigFactory ();
			ccf.setMaxLogins (maxLogins);
			serverFactory.setConnectionConfig (ccf.createConnectionConfig ());
		}
		
		
		serverFactory.setFtplets(Collections.singletonMap("MyFtpLet", new HoneyFtpLet()));
		
		return serverFactory.createServer ();
	}
	
	public void addFtpUsers(List<FtpUser> users) {
		UserManager userManager = userManagerFactory.createUserManager ();
		
		List <Authority> authorities = new ArrayList <Authority> ();
		authorities.add (new WritePermission ());
		
		for (FtpUser ftpUser : users) {
			BaseUser newUSer = new BaseUser();
			newUSer.setName(ftpUser.getUserName());
			newUSer.setPassword(ftpUser.getPassword());
			newUSer.setHomeDirectory(home);
			if(ftpUser.getUserPermission() == null || ftpUser.getUserPermission().equals(FtpUser.FtpPermission.WRITE))
				newUSer.setAuthorities(authorities);
			
			userManager.save(newUSer);
		}
		
//		BaseUser userRd = new BaseUser ();
//		userRd.setName (readUserName);
//		userRd.setPassword (readUserPwd);
//		userRd.setHomeDirectory (ftpHomeDir);
//		
//		BaseUser userWr = new BaseUser ();
//		userWr.setName (writeUserName);
//		userWr.setPassword (writeUserPwd);
//		userWr.setHomeDirectory (ftpHomeDir);
//		
//		if (maxIdleTimeSec > 0) {
//			userRd.setMaxIdleTime (maxIdleTimeSec);
//			userWr.setMaxIdleTime (maxIdleTimeSec);
//		}
		
		
		userWr.setAuthorities (authorities);
		
		userManager.save (userRd);
		userManager.save (userWr);
		
	}
	
	public void addFtpUser()
	













}
