package trapManagementServer.http.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HTTP_ServerMVCConfig implements WebMvcConfigurer {
	
	@Autowired
	private HttpRequestsInterceptor httpReqInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		System.out.println("Config");
		registry.addInterceptor(httpReqInterceptor).addPathPatterns("/**/");
	}

}
