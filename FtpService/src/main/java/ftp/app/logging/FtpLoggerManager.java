package ftp.app.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import ftp.app.connections.DateFormatter;
import ftp.app.connections.JsonObserver;
import ftp.app.connections.RequestFormat;




public class FtpLoggerManager {
	
	
	private final Logger logger = Logger.getLogger(FtpLoggerManager.class.getName());
	private final String LOGGERFILEPATH = "D:\\java\\HoneyPot\\TrapManagement\\Logs\\FTP_tmpLog\\";
	private List<RequestFormat> actionsToStore;
	private List<JsonObserver> jsonObservers;
	
	
	//TODO: add constructor, that creates new log file on each connection - for onLogin
	//TODO: resolve concurrent connection, different loggers needed
	
	public FtpLoggerManager() {
			
			logger.setUseParentHandlers(false); 	//don't print to console
			this.jsonObservers = new ArrayList<>();
			
		
	}
	
	public void registerJsonObserver(JsonObserver observer) {
		this.jsonObservers.add(observer);
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
	
//	private String getCurrentDateTime() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy__HH-mm-ss__");
//		return  dateFormat.format(new Date());
//	}
	
	private void addActionToList(String action) {
		System.out.println("\nWriting to log\n");
		this.actionsToStore.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog(), action));
		logger.info(action);
	}
	
	//first method to be invoked 
	public void onConnect(String clientIP) {
		String logFileName = DateFormatter.getCurrentDateTimeForFile().concat("_" + clientIP + ".log");
		setLoggerFile(logFileName);
		this.actionsToStore = new ArrayList<>();
		String action = "New FTP connection request from client: " + clientIP;
		addActionToList(action);
		
	}
	
	public void onDisconnect(String clientIp) {
		String action = "Client with ip address: " + clientIp + ", has disconnected from the server";
		addActionToList(action);
		notifyJsonObservers();
		
		//TODO: send the list to trapManager so it can send it to monitor
	}
	
	public void onLogin(String userName, String userPwd) {
		//TODO : optional, check weather the user is one of the FTP users in DB and print a log msg accordingly 
		String action = "The user: " + userName + ", is trying to connect to FTP server, with password: " + userPwd;
		addActionToList(action);
	}
	
	public void onDownload(String actions) {
		
	}
	
	public void onUpload(String actions) {
		
	}
	
	public void onDelete(String fileName) {
		
	}
	
	public void onCommand(String actions) {
		String action = "command: " + actions;
		addActionToList(action);
//		logger.info(action);
	}
	
	private void notifyJsonObservers() {
		for (JsonObserver observer : this.jsonObservers) {
			observer.notifyJsonSaved(this.actionsToStore);
		}
	}
	

}
