package trapManagementServer.ftp.model;

import java.io.IOException;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;

public class HoneyFtpUtils {

	public static final int FTP_PORT = /*21;*/21;
	public static final String FTP_HOST = "localhost";
	private static final String FTP_HOME_DIR = "target/FtpHome";
	private static final String FTPUSERSPROPS_FILE = "target/FtpUsers.properties";
	private static final String READ_USER_NAME = "ReadUserName";
	private static final String READ_USER_PWD = "ReadUserPwd";
	private static final String WRITE_USER_NAME = "WriteUserName";
	private static final String WRITE_USER_PWD = "WriteUserPwd";
	private static FtpServer ftpServer;

	//for ALPHA version
	public static void startFtpServer () throws FtpException, IOException
	{
		ftpServer = HoneyFtpConfigure.createFtpServer (FTP_PORT, FTP_HOME_DIR,
				READ_USER_NAME, READ_USER_PWD, WRITE_USER_NAME, WRITE_USER_PWD, FTPUSERSPROPS_FILE, 0);
		ftpServer.start();			

	}

}
