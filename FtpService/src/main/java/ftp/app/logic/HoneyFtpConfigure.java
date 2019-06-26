package ftp.app.logic;

import java.io.IOException;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;

import java.io.*;
import java.util.*;
import org.apache.ftpserver.*;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.*;
import org.apache.ftpserver.usermanager.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ftp.app.model.FtpUser;

@Component
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

	private FtpServerFactory serverFactory;
	private PropertiesUserManagerFactory userManagerFactory;
	private Environment env;
	
	
	@Autowired
	public HoneyFtpConfigure(FtpServerFactory serverFactory, PropertiesUserManagerFactory userManagerFactory, Environment env) {
		this.serverFactory = serverFactory;
		this.userManagerFactory = userManagerFactory;
		this.env = env;
	}
	

	public FtpServer createFtpServer (int ftpPort, String ftpHomeDir,
			String readUserName, String readUserPwd, String writeUserName, String writeUserPwd,
			String ftpUsersPropsFile, int maxLogins, int maxIdleTimeSec) throws FtpException, IOException
	{

		File fhd = new File (ftpHomeDir);
		System.out.println("\n\nIN CreateFtpServer, ftmHome: " + fhd);
		if (!fhd.exists())
			fhd.mkdirs();

		ListenerFactory listenerFactory = new ListenerFactory ();
		listenerFactory.setPort (ftpPort);
		UserManager userManager = userManagerFactory.createUserManager ();

		userManagerFactory.setPasswordEncryptor (new SaltedPasswordEncryptor ());
		if (ftpUsersPropsFile != null && ftpUsersPropsFile.trim (). length ()> 0) {
			File upf = new File (ftpUsersPropsFile);
			System.out.println("\n\nFile Name: " + upf.getName());
			File containingFolder = new File(ftpUsersPropsFile.replace(upf.getName(), ""));
			
			if(!containingFolder.exists())
				containingFolder.mkdir();
			if (!upf.exists()) 
				upf.createNewFile();
			
				
			userManagerFactory.setFile(upf);
		}
		
				serverFactory.addListener ("default", listenerFactory.createListener());
				serverFactory.setUserManager (userManager);


		if (maxLogins > 0) {
			ConnectionConfigFactory ccf = new ConnectionConfigFactory ();
			ccf.setMaxLogins (maxLogins);
			serverFactory.setConnectionConfig (ccf.createConnectionConfig ());
		}


		serverFactory.setFtplets(Collections.singletonMap("MyFtpLet", new HoneyFtpLet()));

		return serverFactory.createServer ();
	}

	public void addFtpUsers(List<FtpUser> users)  {

		for (FtpUser ftpUser : users) {
			
			try {
				addFtpUser(ftpUser);
			} catch (FtpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	public void addFtpUser(FtpUser ftpUser) throws FtpException {
		UserManager userManager = userManagerFactory.createUserManager ();
		System.out.println("\n\nAdding new user to ftp: " + ftpUser);
		
		List <Authority> authorities = new ArrayList <Authority> ();
		authorities.add (new WritePermission ());
		
		BaseUser newUSer = new BaseUser();
		newUSer.setName(ftpUser.getUserName());
		newUSer.setPassword(ftpUser.getPassword());
		newUSer.setHomeDirectory(this.env.getProperty("ftpServer.homeDir"));
		if(ftpUser.getUserPermission() == null || ftpUser.getUserPermission().equals(FtpUser.FtpPermission.WRITE))
			newUSer.setAuthorities(authorities);

		userManager.save(newUSer);
		serverFactory.setUserManager (userManager);

	}














}
