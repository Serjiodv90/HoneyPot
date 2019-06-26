package http.app.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
public class webConfig implements WebMvcConfigurer{
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	} 
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	System.out.println("WebConfig.addResourceHandlers()");
    	
        registry.addResourceHandler(
//                "/webjars/**",
                "/Images/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(
//                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/Images/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }
 
    
    
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/login").setViewName("afekaLogin");
//      registry.addViewController("/").setViewName("afekaLogin");
//      registry.addViewController("/dashboard").setViewName("dashboard");
//      registry.addViewController("/login").setViewName("login");
      System.out.println("PageConfig.addViewControllers()\n" + registry.toString());
  }
    

    

}
