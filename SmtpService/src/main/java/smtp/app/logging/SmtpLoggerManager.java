package smtp.app.logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import smtp.app.SpringContext;
import smtp.app.config.SmtpConfiguration;
import smtp.app.connection.DateFormatter;
import smtp.app.connection.JsonDelegatorConnection;
import smtp.app.connection.RequestFormat;
import smtp.app.logging.SmtpLoggerFormatter;

public class SmtpLoggerManager {

	private final Logger LOGGER = Logger.getLogger(SmtpLoggerFormatter.class.getName());
	private final String LOGGERFILEPATH = "./Logs/SMTPLogs/";
	private List<RequestFormat> actionsToStore;
	private final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";

	//values in the email body to represent text body or text file attached 
	private final String CONTENT_TYPE_FIELD = "Content-Type:";
	private final String CONTENT_TYPE_TEXT_VALUE = "text/plain;";
	private final String lAST_LINE_OF_MULTIPART_BEFORE_FILE = "filename";
	
	private ApplicationContext context;

	public SmtpLoggerManager() {
		this.LOGGER.setUseParentHandlers(false);
		this.context = new AnnotationConfigApplicationContext(SmtpConfiguration.class);
	}



	private void addActionToList(String action) {
		System.out.println("\nWriting to log\n");
		this.actionsToStore.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog(), action));
		this.LOGGER.info(action);
	}

	private void setLoggerFile(String filePath) {
		FileHandler fileHandler;
		try {
			File logsDir = new File(LOGGERFILEPATH);
			if(!logsDir.exists())
				logsDir.mkdirs();

			System.err.println("\n\nLogs Dir: " + logsDir + "root folder: " + new File("."));
			fileHandler = new FileHandler(LOGGERFILEPATH.concat(filePath));
			fileHandler.setFormatter(new SmtpLoggerFormatter());
			this.LOGGER.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

	}

	public void onConnect(String clientIp) {
		Pattern ipv4Pattern = Pattern.compile(this.ipv4Pattern, Pattern.CASE_INSENSITIVE);
		Matcher ipMatcher = ipv4Pattern.matcher(clientIp);
		if(true == ipMatcher.find())
			clientIp = ipMatcher.group();
		String logFileName = DateFormatter.getCurrentDateTimeForFile().concat("_" + clientIp + ".log");
		setLoggerFile(logFileName);

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

	public void data(InputStream data) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(data));
		boolean isMultipart = false, isNotTextFileAttached = false;
		String emailContent = "";

		while(reader.ready()) {
			String line = reader.readLine();


			if(line.contains(CONTENT_TYPE_FIELD)) {
				if(!isMultipart) {
					if(!line.contains(CONTENT_TYPE_TEXT_VALUE)) { //email has multipart message - there is a file 
						isMultipart = true;
					}
				}
				else {
					if(!line.contains(CONTENT_TYPE_TEXT_VALUE)) { //email has contains file which isn't text file, so don't save it content
						isNotTextFileAttached = true;
					}
				}
			}

			//add line to log
			emailContent += line + "\n";

			if(isNotTextFileAttached)
				if(line.contains(lAST_LINE_OF_MULTIPART_BEFORE_FILE))	//the last line of email content before the non-textual file is read
					break;
		}

		emailContent = "Email Content:\n" + emailContent;
		System.out.println("Content: " + emailContent);
		addActionToList(emailContent);
	}
	
	public void done() {
		delegateJson();
	}

	private void delegateJson() {
//		((JsonDelegatorConnection)(this.context.getBean("JsonDelegatorConnection")))
//		.sendJsonToJsonDelegator(this.actionsToStore.toArray(new RequestFormat[this.actionsToStore.size()]));
//		
		JsonDelegatorConnection beanInstance = SpringContext.getBean(JsonDelegatorConnection.class);
		beanInstance.sendJsonToJsonDelegator(this.actionsToStore.toArray(new RequestFormat[this.actionsToStore.size()]));
	}

}
