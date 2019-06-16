package trapManagement.app.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import trapManagement.app.trapsGenerator.TrapsManager;

@Configuration
public class TrapsConfiguration implements WebMvcConfigurer {


	private TrapsManager manager;


	@Autowired
	public TrapsConfiguration(TrapsManager manager) {
		this.manager = manager;
	}



	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.manager).addPathPatterns("/organizationDetails");
		
	}



}
