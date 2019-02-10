package com.example.demo.aop;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class HttpRequestsInterceptor extends HandlerInterceptorAdapter {
	private final String LOGGER_PATH = "C:\\Users\\DELL\\Documents\\workspace-sts-3.9.7.RELEASE\\myServerJPA\\src\\logFiles\\";
	private final static Logger LOGGER = Logger.getLogger("HTTP Log2");
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
      Object handler) {
		
		try {
			LOGGER.setUseParentHandlers(false);
			Handler fileHandler = new FileHandler(LOGGER_PATH + "check.log");
			fileHandler.setFormatter(new LoggerFormatter());
			LOGGER.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(request.getParameter("userName"));
        return true;
    }
}
