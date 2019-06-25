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
                "/img/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(
//                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }
    
//    @Bean
//    public ViewResolver jspViewResolver() {
//      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//      viewResolver.setPrefix("/WEB-INF/jsp/");
//      viewResolver.setSuffix(".jsp");
//      return viewResolver;
//    }
//  
    
//    @Bean
//    public ClassLoaderTemplateResolver templateResolver() {
//
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//
//        templateResolver.setPrefix("templates/");
//        templateResolver.setCacheable(false);
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setCharacterEncoding("UTF-8");
//
//        return templateResolver;
//    }
//    
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//
//    	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//
//        return templateEngine;
//    }
//    
//    
//    @Bean
//    public ViewResolver viewResolver() {
//
//    	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setCharacterEncoding("UTF-8");
//
//        return viewResolver;
//    }
    
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//    	ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//    	resolver.setTemplateEngine(theTemplateEngine());
//    	resolver.setCharacterEncoding("UTF-8");
//    	registry.viewResolver(resolver);
//    }
//    
//   
//    @Bean
//    public SpringResourceTemplateResolver templateResolver(){
//        // SpringResourceTemplateResolver automatically integrates with Spring's own
//        // resource resolution infrastructure, which is highly recommended.
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setApplicationContext(this.applicationContext);
//        templateResolver.setPrefix("/templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setCharacterEncoding("UTF-8");
//        // HTML is the default value, added here for the sake of clarity.
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        // Template cache is true by default. Set to false if you want
//        // templates to be automatically updated when modified.
////        templateResolver.setCacheable(true);
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine theTemplateEngine(){
//        // SpringTemplateEngine automatically applies SpringStandardDialect and
//        // enables Spring's own MessageSource message resolution mechanisms.
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
//        // speed up execution in most scenarios, but might be incompatible
//        // with specific cases when expressions in one template are reused
//        // across different data types, so this flag is "false" by default
//        // for safer backwards compatibility.
//        templateEngine.setEnableSpringELCompiler(true);
//        return templateEngine;
//    }
    
    
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/login").setViewName("afekaLogin");
//      registry.addViewController("/").setViewName("afekaLogin");
//      registry.addViewController("/dashboard").setViewName("dashboard");
//      registry.addViewController("/login").setViewName("login");
      System.out.println("PageConfig.addViewControllers()\n" + registry.toString());
  }
    

    

}
