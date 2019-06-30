package ftp.app.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ftp.app.ConfigurationJsonDelegator;
import ftp.app.SpringContext;
import ftp.app.connections.DateFormatter;
import ftp.app.connections.JsonDelegatorConnection;
import ftp.app.connections.RequestFormat;

public class FtpLoggerManager {


	private final Logger logger = Logger.getLogger(FtpLoggerManager.class.getName());
	private final String LOGGERFILEPATH = "./Logs/FTPLogs/";
	private List<RequestFormat> actionsToStore;
	ApplicationContext context;

	public FtpLoggerManager() {
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
			e.printStackTrace();
		}

	}

	private void addActionToList(String action) {
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
		String action = "The user: " + userName + ", is trying to connect to FTP server, with password: " + userPwd;
		logger.info(action);
	}

	public void onCommand(String actions) {
		String action = "command: " + actions;
		addActionToList(action);
	}

	public void delegateJson() {
		JsonDelegatorConnection beanInstance = SpringContext.getBean(JsonDelegatorConnection.class);
		beanInstance.sendJsonToJsonDelegator(this.actionsToStore.toArray(new RequestFormat[this.actionsToStore.size()]));
	}


}
