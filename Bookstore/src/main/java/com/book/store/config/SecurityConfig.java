package com.book.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.book.store.filter.JwtAuthenticationFilter;
import com.book.store.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig  {
	
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean 
	 UserDetailsService userDetailsService() { 
		return customUserDetailsService;
	}
	
	@Bean 
	 SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception
	{
		http .csrf(csrf -> csrf.disable()) 
		.authorizeHttpRequests(auth -> auth 
		.requestMatchers("/api/user/register", "/api/user/login","/api/user/logintest","/api/books/allbooks","/api/books/getById/**",
				"/api/orders/place","/swagger-ui/**","/v3/api-docs/**",
                "/swagger-ui.html").permitAll() 
		.requestMatchers("/api/books/create","/api/books/update/**","/api/books/delete/**","/api/books/createbook").hasRole("ADMIN") 
//		.requestMatchers("/api/orders/place").hasRole("USER") // Both ADMIN and CUSTOMER can view orders
        .requestMatchers("/api/orders/**").hasRole("ADMIN") 
		.anyRequest().authenticated() ) 
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 
		        
		return http.build(); 
		}
	
	
	
}