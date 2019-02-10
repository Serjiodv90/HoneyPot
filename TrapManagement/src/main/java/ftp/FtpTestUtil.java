package ftp;

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


/**
 * FTP test utility based on Apache FtpServer:
 * {@link " http://www.jarvana.com/jarvana/view/org/apache/ftpserver/ftpserver-core/1.0.6/ftpserver-core-1.0.6-javadoc.jar!/org/apache/ ftpserver / FtpServer.html "}
 */ 

public class FtpTestUtil {


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
	public static FtpServer createFtpServer (int ftpPort, String ftpHomeDir, String readUserName, String readUserPwd, String writeUserName, String writeUserPwd) throws FtpException, IOException
	{
		return createFtpServer (ftpPort, ftpHomeDir, readUserName, readUserPwd, writeUserName, writeUserPwd, null, 0);
		//      return createFtpServer(ftpPort, ftpHomeDir, readUserName, readUserPwd, writeUserName, writeUserPwd);
	}

	public static FtpServer createFtpServer (int ftpPort, String ftpHomeDir,
			String readUserName, String readUserPwd, String writeUserName, String writeUserPwd,
			String ftpUsersPropsFile, int maxLogins) throws FtpException, IOException
	{
		return createFtpServer (ftpPort, ftpHomeDir, readUserName, readUserPwd, writeUserName, writeUserPwd,
				ftpUsersPropsFile, maxLogins, 0);
	}

	public static FtpServer createFtpServer (int ftpPort, String ftpHomeDir,
			String readUserName, String readUserPwd, String writeUserName, String writeUserPwd,
			String ftpUsersPropsFile, int maxLogins, int maxIdleTimeSec) throws FtpException, IOException
	{
		File fhd = new File (ftpHomeDir);
		if (!fhd.exists ())
			fhd.mkdirs ();

		ListenerFactory listenerFactory = new ListenerFactory ();
		listenerFactory.setPort (ftpPort);

		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory ();
		userManagerFactory.setPasswordEncryptor (new SaltedPasswordEncryptor ());
		if (ftpUsersPropsFile != null && ftpUsersPropsFile.trim (). length ()> 0) {
			File upf = new File (ftpUsersPropsFile);
			if (!upf.exists ()) 
				upf.createNewFile ();
			userManagerFactory.setFile(upf);
		}

		// Create a read-only user and a user with write permission:
		UserManager userManager = userManagerFactory.createUserManager ();
		BaseUser userRd = new BaseUser ();
		userRd.setName (readUserName);
		userRd.setPassword (readUserPwd);
		userRd.setHomeDirectory (ftpHomeDir);
		
		BaseUser userWr = new BaseUser ();
		userWr.setName (writeUserName);
		userWr.setPassword (writeUserPwd);
		userWr.setHomeDirectory (ftpHomeDir);
		if (maxIdleTimeSec > 0) {
			userRd.setMaxIdleTime (maxIdleTimeSec);
			userWr.setMaxIdleTime (maxIdleTimeSec);
		}
		List <Authority> authorities = new ArrayList <Authority> ();
		authorities.add (new WritePermission ());
		userWr.setAuthorities (authorities);
		userManager.save (userRd);
		userManager.save (userWr);

		FtpServerFactory serverFactory = new FtpServerFactory ();
		serverFactory.addListener ("default", listenerFactory.createListener ());
		serverFactory.setUserManager (userManager);
		
		if (maxLogins > 0) {
			ConnectionConfigFactory ccf = new ConnectionConfigFactory ();
			ccf.setMaxLogins (maxLogins);
			serverFactory.setConnectionConfig (ccf.createConnectionConfig ());
		}
		
		return serverFactory.createServer ();
	}













}
