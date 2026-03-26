package com.harish.TickIt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.harish.TickIt.Authentication.CustomAuthenticationProvider;
import com.harish.TickIt.Authentication.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig 
{
	@Autowired
	private CustomAuthenticationProvider custom;
	
	@Bean
	HttpSecurity configure(HttpSecurity http) throws Exception
	{
		http.csrf(r->r.disable())
			.authorizeHttpRequests(a->a.requestMatchers("/api/register", "/api/login").authenticated()
					.anyRequest().permitAll())
			.formLogin(r->r.disable())
			.httpBasic(r->r.disable())
			.authenticationProvider(custom)
			.sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.build();
			
		return http;
				
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		return http.getSharedObject(AuthenticationManager.class);
	}
}
