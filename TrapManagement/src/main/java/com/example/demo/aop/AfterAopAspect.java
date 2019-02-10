package com.example.demo.aop;

import java.io.IOException;
import java.util.logging.*;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;


@Aspect
@Configuration
public class AfterAopAspect {
	
	private final String LOGGER_PATH = "C:\\Users\\DELL\\Documents\\workspace-sts-3.9.7.RELEASE\\myServerJPA\\src\\logFiles\\";
	private final static Logger LOGGER = Logger.getLogger("HTTP Log");

	public AfterAopAspect() {
		try {
			LOGGER.setUseParentHandlers(false);
			Handler handler = new FileHandler(LOGGER_PATH + "login.log");
			handler.setFormatter(new LoggerFormatter());
			LOGGER.addHandler(handler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Before("execution(public String com.example.demo.model.UserService.login(..)) && args(user)")
	public void loginLog(User user) {

		LOGGER.info("UName: " + user.getUserName() + "\tUPass: " + user.getPassword());
	}
	
}
