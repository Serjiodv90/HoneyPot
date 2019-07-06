package http.app;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import http.app.logging.HttpRequestsInterceptor;

@Configuration
public class HttpSessionConfig {


	HttpRequestsInterceptor httpRequestInterceptor;
	Environment env;

	@Autowired
	public void setHttpRequestInterceptor(HttpRequestsInterceptor httpRequestInterceptor) {
		this.httpRequestInterceptor = httpRequestInterceptor;
	}

	@Autowired
	public void setEnvironment(Environment env) {
		this.env = env;
	}

	@Bean // bean for http session listener
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener() {
			@Override
			public void sessionCreated(HttpSessionEvent se) {           // This method will be called when session created
			}
			@Override
			public void sessionDestroyed(HttpSessionEvent se) {   
				// This method will be automatically called when session destroyed
				if(env.getProperty("service.machine").equalsIgnoreCase("localhost"))
					httpRequestInterceptor.sendToJsonDelegator();/*logToJson();*/
			}
		};
	}
	@Bean   // bean for http session attribute listener
	public HttpSessionAttributeListener httpSessionAttributeListener() {
		return new HttpSessionAttributeListener() {
			@Override
			public void attributeAdded(HttpSessionBindingEvent se) {            // This method will be automatically called when session attribute added

				if(se.getName().equals("userName")) { 
					httpRequestInterceptor.saveUserNameAndPassword();
				}
			}
			@Override
			public void attributeRemoved(HttpSessionBindingEvent se) {      // This method will be automatically called when session attribute removed
			}
			@Override
			public void attributeReplaced(HttpSessionBindingEvent se) {     // This method will be automatically called when session attribute replace
			}
		};
	}
}