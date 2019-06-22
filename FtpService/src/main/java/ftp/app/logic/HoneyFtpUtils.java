package ftp.app.logic;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class HoneyFtpUtils {

	@Value("${ftpServer.port:21}")
	public  int FTP_PORT = 21;
	@Value("${ftpServer.host:localhost}")
	public  String FTP_HOST = "localhost";
	@Value("${ftpServer.homeDir:target/FtpHome}")
	private  String FTP_HOME_DIR = "target/FtpHome";
	@Value("${ftpServer.userProps:target/FtpUsers.properties}")
	private  String FTPUSERSPROPS_FILE = "target/FtpUsers.properties";
	@Value("${ftpServer.readUserName:ReadUserName}")
	private  String READ_USER_NAME = "ReadUserName";
	@Value("${ftpServer.readUserPwd:ReadUserPwd}")
	private  String READ_USER_PWD = "ReadUserPwd";
	@Value("${ftpServer.writeUserName:WriteUserName}")
	private  String WRITE_USER_NAME = "WriteUserName";
	@Value("${ftpServer.writeUserPwd:WriteUserPwd}")
	private  String WRITE_USER_PWD = "WriteUserPwd";
	
	private  FtpServer ftpServer;
	private HoneyFtpConfigure ftpConfigure;
	
	@Autowired
	public void setHoneyFtpConfigure(HoneyFtpConfigure ftpConfigure) {
		this.ftpConfigure = ftpConfigure;
	}

	//for ALPHA version
	@PostConstruct
	public  void startFtpServer () throws FtpException, IOException
	{
		ftpServer = HoneyFtpConfigure.createFtpServer (FTP_PORT, FTP_HOME_DIR,
				READ_USER_NAME, READ_USER_PWD, WRITE_USER_NAME, WRITE_USER_PWD, FTPUSERSPROPS_FILE, 0, 0);
		ftpServer.start();			

		
	}

}
