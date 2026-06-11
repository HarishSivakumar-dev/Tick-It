package com.harish.TickIt.TicketService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig 
{
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	{
		return http.csrf(r->r.disable())
					.authorizeHttpRequests(r->r.requestMatchers("/**").permitAll())
					.httpBasic(t->t.disable())
					.build();
	}

}
