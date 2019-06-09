package http.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import http.app.model.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Bean
//	public PasswordEncoder bCryptPasswordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//	    authProvider.setUserDetailsService(userDetailsService);
//	    authProvider.setPasswordEncoder(bCryptPasswordEncoder());
//	    return authProvider;
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
         .authorizeRequests()
         .antMatchers("/").permitAll()
         .antMatchers("/afekaLogin").permitAll()
         .antMatchers("/signup").permitAll()
         .antMatchers("/storeDetails").permitAll()	//need to be added for each request mapping
         .antMatchers("/dashboard/**").hasAuthority("USER").anyRequest()
         .authenticated().and().csrf().disable().formLogin().successHandler(customizeAuthenticationSuccessHandler)
         .loginPage("/login").failureUrl("/login?error=true")
         .usernameParameter("email")	//must be matching to the 'name' attribute of the 'input' html tag under the 'form'
         .passwordParameter("password")
         .and().logout()
         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
         .logoutSuccessUrl("/").and().exceptionHandling();
	}
	
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider());
//        
//    }
	
	
//	@Bean
//	private UserService userServiceConf() {
//		return new UserService(dao)
//	}
	
	

}
