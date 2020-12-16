package com.ecommerce.config;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	 @Autowired
	  private DataSource dataSource;
	 

	  @Bean
	  public PasswordEncoder passwordEncoder() {
	       return new BCryptPasswordEncoder();
	  }
	  
	  @Autowired
	  public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		
	  authenticationMgr.jdbcAuthentication().dataSource(dataSource)
     .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
	        .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?")
	        .passwordEncoder(new BCryptPasswordEncoder());
	  }

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
		  http.cors();
		  http.csrf().disable();
		  http.authorizeRequests()  
	            .antMatchers("/validateLogin").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	            .antMatchers("/pendingOrders/**","/allOrders", "/getCustomers", "/customer/**","/product/**")
	            .access("hasRole('ROLE_ADMIN')")
	            .antMatchers("/order/**","/checkout/**","/orderConfirmation/**", "/cart/**", "/getCart", "/getCartItems",
	  "/getCartById/**").access("hasRole('ROLE_USER')").anyRequest().permitAll()
	            .and()
	        .httpBasic()
	        .and()  
	        .logout();
	  }
}
