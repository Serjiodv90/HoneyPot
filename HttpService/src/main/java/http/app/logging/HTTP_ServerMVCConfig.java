package http.app.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import http.app.connections.JsonFilesManager;

@Configuration
public class HTTP_ServerMVCConfig implements WebMvcConfigurer {
	
	@Autowired
	private HttpRequestsInterceptor httpReqInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		System.out.println("Config");
		registry.addInterceptor(httpReqInterceptor).addPathPatterns("/**");
		
		JsonFilesManager jsonManager = JsonFilesManager.getInstance();
		httpReqInterceptor.registerObserver(jsonManager);
	}

}
