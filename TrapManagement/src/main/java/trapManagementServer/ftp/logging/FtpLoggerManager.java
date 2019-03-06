package trapManagementServer.ftp.logging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;




public class FtpLoggerManager {
	
	
	private final Logger logger = Logger.getLogger(FtpLoggerManager.class.getName());
	private final String LOGGERFILEPATH = "D:\\java\\HoneyPot\\TrapManagement\\Logs\\FTP_tmpLog\\";
	
	
	//TODO: add constructor, that creates new log file on each connection - for onLogin
	//TODO: resolve concurrent connection, different loggers needed
	
	public FtpLoggerManager() {
			
			logger.setUseParentHandlers(false); 	//don't print to console
			
		
	}
	
	private void setLoggerFile(String filePath) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler(LOGGERFILEPATH.concat(filePath));
			fileHandler.setFormatter(new FtpLogFormatter());
			logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getCurrentDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy__HH-mm-ss__");
		return  dateFormat.format(new Date());
	}
	
	public void onConnect(String clientIP) {
		String logFileName = getCurrentDateTime().concat(clientIP + ".txt");
		setLoggerFile(logFileName);
		logger.info("New connection established from client: " + clientIP);
	}
	
	public void onDisconnect(String clientIp) {
		logger.info("Client with ip address: " + clientIp + ", has disconnected from the server");
	}
	
	public void onLogin(String userName, String userPwd) {
		//TODO : optional, check weather the user is one of the FTP users in DB and print a log msg accordingly 
		
		logger.info("The user :" + userName + ", is trying to connect to FTP server, with password: " + userPwd);
	}
	
	public void onDownload(String actions) {
		
	}
	
	public void onUpload(String actions) {
		
	}
	
	public void onDelete(String fileName) {
		
	}
	
	public void onCommand(String actions) {
		logger.info("command: " + actions);
	}
	

}
