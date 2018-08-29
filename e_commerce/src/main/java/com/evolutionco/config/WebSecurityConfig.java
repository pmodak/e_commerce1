package com.evolutionco.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.evolutionco.authentication.myDBAuthenticationService;
@Configuration
//@EnableWebSecurity=EnableWebMVC+Extra Features
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	 @Autowired
	 myDBAuthenticationService myDBAuthenticationService;
		 
		   @Autowired
		   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			   	System.out.println("Configure Security");
		       // For User in database.
		       auth.userDetailsService(myDBAuthenticationService);
		   }
		   
		   protected void configure(HttpSecurity http) throws Exception {
			   
			   System.out.println("Configure");
		 	//csrf=cross site request forgery
		 	//csrf will be enable by default 
		 	//it is a type of malicious exploit of a website.
	       http.csrf().disable();
	 
	       // The pages requires login as Customer or Admin.
	       // If no login, it will redirect to /login page.
	       http.authorizeRequests().antMatchers("/customerorderList","/order", "/accountInfo")
	               .access("hasAnyRole('ROLE_CUSTOMER','ROLE_ADMIN')");
	 
	       // For Admin only.
	       http.authorizeRequests().antMatchers("/product","/orderList").access("hasRole('ROLE_ADMIN')");
	 
	       // When the user has logged in as XX.
	       // But access a page that requires role YY,
	       // AccessDeniedException will throw.
	       http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
	 
	       // Config for Login Form
	       http.authorizeRequests().and().formLogin()//
	               // Submit URL of login page.
	               .loginProcessingUrl("/j_spring_security_check") // Submit URL
	               .loginPage("/login")//
	               .defaultSuccessUrl("/accountInfo")//
	               .failureUrl("/login?error=true")//
	               .usernameParameter("userName")//
	               .passwordParameter("password")
	               // Config for Logout Page
	               // (Go to home page).
	               .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
	 
	   }
	}

