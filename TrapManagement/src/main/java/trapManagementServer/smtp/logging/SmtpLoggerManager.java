package trapManagementServer.smtp.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

import trapManagementServer.DateFormatter;
import trapManagementServer.JsonObserver;
import trapManagementServer.RequestFormat;

public class SmtpLoggerManager {
	
	private Formatter smtpFormatter = new SmtpLoggerFormatter();
	private final Logger LOGGER = Logger.getLogger(SmtpLoggerFormatter.class.getName());
	private final String LOGGERFILEPATH = "D:/java/HoneyPot/TrapManagement/Logs/SMTP_tmpLog";
	private List<RequestFormat> actionsToStore;
	private List<JsonObserver> jsonObservers;
	
	public SmtpLoggerManager() {
		this.LOGGER.setUseParentHandlers(false);
		this.jsonObservers = new ArrayList<>();
	}
	
	public void registerJsonObserver(JsonObserver observer) { 
		this.jsonObservers.add(observer);
	}
	
	private void addActionToList(String action) {
		System.out.println("\nWriting to log\n");
		this.actionsToStore.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog(), action));
		this.LOGGER.info(action);
	}
	
	public void onConnect(String clientIp) {
		String logFileName = DateFormatter.getCurrentDateTimeForFile().concat("_" + clientIp + ".log");
		try {
			this.LOGGER.addHandler(new FileHandler(LOGGERFILEPATH.concat(logFileName)));
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.actionsToStore = new ArrayList<>();
		String action = "New SMTP connection request from client: " + clientIp;
		addActionToList(action);
	}
	
	public void mailFrom(String senderAddr) { 
		addActionToList("FROM: " + senderAddr);
	}
	
	public void rcptTo(String recipientAddr) { 
		addActionToList("TO: " + recipientAddr); 
	}
	

}
