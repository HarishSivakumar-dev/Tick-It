package com.harish.TickIt.ProjectService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.harish.TickIt.ProjectService.auth.JwtAuthencationFilter;

@Component
public class SecurityConfig
{
	@Autowired
	private JwtAuthencationFilter jwtAuthenticationFilter;
	
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http.csrf(r->r.disable())
				   .authorizeHttpRequests(r -> r.anyRequest().permitAll())
				   .httpBasic(r->r.disable())
				   .formLogin(r->r.disable())
				   .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				   .build();
	}

}
