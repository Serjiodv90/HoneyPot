package ftp.app.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ftp.app.ConfigurationJsonDelegator;
import ftp.app.connections.DateFormatter;
import ftp.app.connections.JsonDelegatorConnection;
import ftp.app.connections.RequestFormat;

//
//
//@Configuration
//@Scope("prototype")
public class FtpLoggerManager {


	private final Logger logger = Logger.getLogger(FtpLoggerManager.class.getName());
	private final String LOGGERFILEPATH = "D:\\java\\HoneyPot\\TrapManagement\\Logs\\FTP_tmpLog\\";
	private List<RequestFormat> actionsToStore;
	private JsonDelegatorConnection delegator;
	

	//TODO: add constructor, that creates new log file on each connection - for onLogin
	//TODO: resolve concurrent connection, different loggers needed

	//	@Autowired
		ApplicationContext context;

	//	@Autowired
	//	public void setDelegator(JsonDelegatorConnection delegator) {
	//		System.err.println("FtpLoggerManager.setDelegator()");
	//		this.delegator = delegator;
	//	}

	public FtpLoggerManager() {
		System.err.println("FtpLoggerManager.FtpLoggerManager()");
		logger.setUseParentHandlers(false); 	//don't print to console
		context = new AnnotationConfigApplicationContext(ConfigurationJsonDelegator.class);

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
		System.out.println("\nWriting to log : " + action + "\n");
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
		delegateJson();
	}

	public void onLogin(String userName, String userPwd) {
		//TODO : optional, check weather the user is one of the FTP users in DB and print a log msg accordingly 
		String action = "The user: " + userName + ", is trying to connect to FTP server, with password: " + userPwd;
//		addActionToList(action);
		logger.info(action);
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

	public void delegateJson() {
		//		this.delegator = new JsonDelegatorConnection();
//		System.err.println("FtpLoggerManager.delegateJson()\nDelegator: " + this.delegator.getHostName());

				((JsonDelegatorConnection)(context.getBean("JsonDelegatorConnection")))
		/*this.delegator*/.sendJsonToJsonDelegator(this.actionsToStore.toArray(new RequestFormat[this.actionsToStore.size()]));
	}


}
