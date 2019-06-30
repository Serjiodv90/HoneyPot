package http.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import http.app.logging.HttpRequestsInterceptor;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class HttpSessionConfig {
	
	
	HttpRequestsInterceptor httpRequestInterceptor;
	
	@Autowired
	public void setHttpRequestInterceptor(HttpRequestsInterceptor httpRequestInterceptor) {
		this.httpRequestInterceptor = httpRequestInterceptor;
	}
	
    @Bean // bean for http session listener
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {           // This method will be called when session created
            }
            @Override
            public void sessionDestroyed(HttpSessionEvent se) {         // This method will be automatically called when session destroyed
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