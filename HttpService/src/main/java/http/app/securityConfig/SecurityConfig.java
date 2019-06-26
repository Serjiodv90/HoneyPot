package http.app.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests().antMatchers("/login", "/forgotPassword", "/changePassword", "/fakeUsers", "/resources/**",
					"/static/**", "/templates/**", "/Images/**", "/js/**", "/stylesheet.css").permitAll()
//			.antMatchers("/").hasAuthority("USER")
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login").failureUrl("/login?error=true").permitAll()
			.and().logout().invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/logout-success").permitAll();
	}
	
	
	@Bean
	public AuthenticationProvider authProvider() { 
		System.err.println("in authProvider()");
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		 //Bcrypt hashes the password within a few rounds
		return provider;
	}

	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/stylesheet.css", "/static/**", "/js/**", "/Images/**");
    }
//	
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//
//
//		List<UserDetails> users = new ArrayList<>();
//		users.add(User.withDefaultPasswordEncoder().username("tair").password("1234").roles("USER").build());
//		
//		return new InMemoryUserDetailsManager(users);
//		
//	}
	
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
