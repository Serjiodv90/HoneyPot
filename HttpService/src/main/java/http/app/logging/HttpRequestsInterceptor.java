package http.app.logging;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import http.app.MyUserPrincipal;
import http.app.connections.DateFormatter;
import http.app.connections.JsonDelegatorConnection;
import http.app.connections.RequestFormat;


@Component
public class HttpRequestsInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = Logger.getLogger("HTTP Log");
	ArrayList<RequestFormat> reqArrList = new ArrayList<RequestFormat>();
	private static Handler fileHandler;
	private String ipAddress = "";
	
	private JsonDelegatorConnection connection;
	private Environment env;
	
	@Autowired
	public HttpRequestsInterceptor(JsonDelegatorConnection connection, Environment env) {
		this.connection = connection;
		this.env = env;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) {

		StringBuffer requestBody = new StringBuffer();

		requestBody.append("\tHTTP --> ");
		requestBody.append(request.getMethod() + " ");
		
		// save connection for the first time
		String tmpIpAddress = request.getHeader("X-FORWARDED-FOR");  
		if (tmpIpAddress == null) {  
			tmpIpAddress = request.getRemoteAddr();
			if(tmpIpAddress.equals("0:0:0:0:0:0:0:1"))	//for localhost 
				tmpIpAddress = "127.0.0.1"; 
			if(this.ipAddress.isEmpty() || !tmpIpAddress.equals(this.ipAddress)) {
				this.ipAddress = tmpIpAddress;
				requestBody.append("New connection request from: " + ipAddress);
			}
		}
		
		String date = DateFormatter.getCurrentDateTimeForFile();
		setLogger(ipAddress, date);

		Map<String, String> params = new HashMap<>();
		Map<String, String[]> parameterMap = request.getParameterMap();

		parameterMap.forEach((key,value) -> { params.put(key, value[0]); });

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			requestBody.append(entry.getKey()+" : ");
			for(String s : entry.getValue())
				requestBody.append(s + ", ");
		}

		LOGGER.info(requestBody.toString());
		reqArrList.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog(),
				requestBody.toString()));

		return true;
	}

	private void setLogger(String clientIp, String date) {
		String loggerPath = env.getProperty("http.log.files");
		File logDir = new File(loggerPath);
		if(!logDir.exists())
			logDir.mkdir();
			
		if(fileHandler == null)
			try {
				LOGGER.setUseParentHandlers(false);
				fileHandler = new FileHandler(loggerPath + date + "_" + clientIp);
				fileHandler.setFormatter(new LoggerFormatter());
				LOGGER.addHandler(fileHandler);
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}
	}

	public void sendToJsonDelegator() {
		connection.sendJsonToJsonDelegator(reqArrList.toArray(new RequestFormat[reqArrList.size()]));
		reqArrList.clear();
	}

	public void saveUserNameAndPassword() {
		StringBuffer requestBody = new StringBuffer();
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user instanceof MyUserPrincipal) {
			System.err.println(((MyUserPrincipal)user).getUsername());
			requestBody.append("\tHTTP --> ");
			requestBody.append("POST ");
			requestBody.append("username: " + ((MyUserPrincipal)user).getUsername() 
					+ " password: " + ((MyUserPrincipal)user).getPassword());
		}
		LOGGER.info(requestBody.toString());
		reqArrList.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog(),	requestBody.toString()));
		
	}
}
