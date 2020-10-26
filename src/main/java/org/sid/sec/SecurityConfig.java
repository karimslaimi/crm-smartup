 package org.sid.sec;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	@Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource ;
    
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }
    
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 
 		
	 	
	 	/*auth.inMemoryAuthentication().withUser("admin").password(bcpe.encode("1234")).roles("ADMIN","USER") ;  
		auth.inMemoryAuthentication().withUser("user").password(bcpe.encode("1234")).roles("USER") ;  
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()) ;
	*/
    	PasswordEncoder bcpe=passwordEncoder() ;

	 auth.jdbcAuthentication()
	 .dataSource(dataSource) 
	 .usersByUsernameQuery("select username as principal , password as credentials , active from users where username=?")
	 .authoritiesByUsernameQuery("select username as principal, role as role from users where username=? ")
	 .rolePrefix("ROLE_")
	 .passwordEncoder(bcpe)  ;
	
	
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
	http.formLogin().loginPage("/login").successHandler(authenticationSuccessHandler) ;	
	http.authorizeRequests().antMatchers("/admin/*").hasAnyRole("ADMIN") ;
	http.authorizeRequests().antMatchers("/user/*").hasAnyRole("CLIENT","ADMIN") ;
	http.authorizeRequests().antMatchers("/client/*").hasRole("CLIENT") ;
	//http.authorizeRequests().antMatchers("/Reclamation/ajouter").hasRole("CLIENT") ;
	http.exceptionHandling().accessDeniedPage("/403") ;
	http.logout()  
	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	.logoutSuccessUrl("/login")
	.invalidateHttpSession(true)
	.deleteCookies("JSESSIONID");	
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}	
}
