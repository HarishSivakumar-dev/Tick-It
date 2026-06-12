package com.harish.TickIt.TicketService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.harish.TickIt.TicketService.auth.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig 
{
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	{
		return http.csrf(r->r.disable())
					.authorizeHttpRequests(r->r.requestMatchers("/**").permitAll())
					.httpBasic(t->t.disable())
					.formLogin(t->t.disable())
					.addFilter(jwtAuthenticationFilter, new UsernamePasswordAuthenticationFilter())
					.build();
	}

}
