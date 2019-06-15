package http.app.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import http.app.MyUserPrincipal;
import http.app.connections.DateFormatter;
import http.app.connections.JsonObserver;
import http.app.connections.JsonSave;
import http.app.connections.RequestFormat;


@Component
public class HttpRequestsInterceptor extends HandlerInterceptorAdapter implements JsonSave {

	private final String LOGGER_PATH = /*"D:/java/HoneyPot/TrapManagement/Logs/HTTP_tmpLog/"*/"C:\\Users\\DELL\\Documents\\Honeypot\\projects\\HoneyPot\\HttpService\\src\\logFiles\\";
	private final static Logger LOGGER = Logger.getLogger("HTTP Log");
	//	private final static String JSON_PATH ="C:\\Users\\DELL\\Documents\\workspace-sts-3.9.7.RELEASE\\TrapManagement\\src\\JsonFiles";
	private final static String JSON_FILE_NAME = "\\HTTPLog.json";
	ArrayList<RequestFormat> reqArrList = new ArrayList<RequestFormat>();
	private static Handler fileHandler;
	private List<JsonObserver> observers = new ArrayList<JsonObserver>();
	private String ipAddress = "";
	

//	public void sessionCreated (String ipAddress) {
//		StringBuffer requestBody = new StringBuffer();
//		requestBody.append("\tHTTP --> ");
//		requestBody.append("New connection request from: " + ipAddress);
//
//		LOGGER.info(requestBody.toString());
//		reqArrList.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog(), requestBody.toString()));
//	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) {
		
		
		
		System.err.println();

		StringBuffer requestBody = new StringBuffer();

		System.out.println("in preHandle");

		requestBody.append("\tHTTP --> ");
		requestBody.append(request.getMethod() + " ");
		
		
//		System.err.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if(user instanceof MyUserPrincipal) {
//			System.err.println(((MyUserPrincipal)user).getUsername());
//			requestBody.append("\tHTTP --> ");
//			requestBody.append("POST ");
//			requestBody.append(" username: " + ((MyUserPrincipal)user).getUsername() 
//					+ "password: " + ((MyUserPrincipal)user).getPassword());
//		}
		
		
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
		//		System.out.println("HttpRequestsInterceptor.preHandle()\nIp Addr: " + ipAddress);

		Map<String, String> params = new HashMap<>();
		Map<String, String[]> parameterMap = request.getParameterMap();

		parameterMap.forEach((key,value) -> { params.put(key, value[0]); });

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			requestBody.append(entry.getKey()+" : ");
			for(String s : entry.getValue())
				requestBody.append(s + ", ");
		}

		LOGGER.info(requestBody.toString());
		reqArrList.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog()
				/*LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))*/,
				requestBody.toString()));

		return true;
	}

	private void setLogger(String clientIp, String date) {
		if(fileHandler == null)
			try {
				LOGGER.setUseParentHandlers(false);
				fileHandler = new FileHandler(LOGGER_PATH + date + "_" + clientIp/*"httpRequests.log"*/);
				fileHandler.setFormatter(new LoggerFormatter());
				LOGGER.addHandler(fileHandler);
			} catch (SecurityException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	//	public void logToJson() {
	//		try (Writer writer = new FileWriter(JSON_PATH + JSON_FILE_NAME)) {
	//			Gson gson = new GsonBuilder().create();
	//			gson.toJson(reqArrList, writer);
	//			notifyAllRegistered();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//	}

	@Override
	public void registerObserver(JsonObserver obs) {
		System.out.println("in httptequestinter- register");
		observers.add(obs);

	}

	@Override
	public void notifyAllRegistered() {
		System.out.println("notifyall");
		for(JsonObserver obs : observers) {
			System.out.println("in loop obs");
			obs.notifyJsonSaved(reqArrList);
		}

	}

	public void saveUserNameAndPassword() {
		StringBuffer requestBody = new StringBuffer();
		System.err.println("saveUserNameAndPassword");
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user instanceof MyUserPrincipal) {
			System.err.println(((MyUserPrincipal)user).getUsername());
			requestBody.append("\tHTTP --> ");
			requestBody.append("POST ");
			requestBody.append("username: " + ((MyUserPrincipal)user).getUsername() 
					+ " password: " + ((MyUserPrincipal)user).getPassword());
		}
		LOGGER.info(requestBody.toString());
		reqArrList.add(new RequestFormat(DateFormatter.getCurrentDateTimeForLog()
				/*LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))*/,
				requestBody.toString()));
		
	}
}
