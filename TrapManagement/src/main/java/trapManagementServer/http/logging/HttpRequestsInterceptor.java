package trapManagementServer.http.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import trapManagementServer.JsonObserver;
import trapManagementServer.JsonSave;
import trapManagementServer.RequestFormat;


@Component
public class HttpRequestsInterceptor extends HandlerInterceptorAdapter implements JsonSave {

	private final String LOGGER_PATH = "C:\\Users\\DELL\\Documents\\Honeypot\\projects\\HoneyPot\\TrapManagement\\src\\logFiles\\";
	private final static Logger LOGGER = Logger.getLogger("HTTP Log");
//	private final static String JSON_PATH ="C:\\Users\\DELL\\Documents\\workspace-sts-3.9.7.RELEASE\\TrapManagement\\src\\JsonFiles";
	private final static String JSON_FILE_NAME = "\\HTTPLog.json";
	ArrayList<RequestFormat> reqArrList = new ArrayList<RequestFormat>();
	private static Handler fileHandler;
	
	private List<JsonObserver> observers = new ArrayList<JsonObserver>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) {
		StringBuffer requestBody = new StringBuffer();

		System.out.println("in preHandle");
		setLogger();
		
		requestBody.append("HTTP - ");
		requestBody.append(request.getMethod() + " ");

		Map<String, String> params = new HashMap<>();
		Map<String, String[]> parameterMap = request.getParameterMap();

		parameterMap.forEach((key,value) -> { params.put(key, value[0]); });

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			requestBody.append(entry.getKey()+" : ");
			for(String s : entry.getValue())
				requestBody.append(s + ", ");
		}

		LOGGER.info(requestBody.toString());
		reqArrList.add(new RequestFormat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
				requestBody.toString()));

		return true;
	}
	
	private void setLogger() {
		if(fileHandler == null)
			try {
				LOGGER.setUseParentHandlers(false);
				fileHandler = new FileHandler(LOGGER_PATH + "httpRequests.log");
				fileHandler.setFormatter(new LoggerFormatter());
				LOGGER.addHandler(fileHandler);
			} catch (SecurityException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void logToJson() {
		try (Writer writer = new FileWriter(JSON_PATH + JSON_FILE_NAME)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(reqArrList, writer);
			notifyAllRegistered();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
			obs.notifyJsonSaved();
		}
		
	}
}
