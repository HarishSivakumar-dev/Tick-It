package com.harish.TickIt.ProjectService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.harish.TickIt.ProjectService.auth.JwtAuthencationFilter;

@Component
@EnableWebSecurity
public class SecurityConfig
{
	@Autowired
	private JwtAuthencationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http.csrf(r->r.disable())
				   .authorizeHttpRequests(r -> r.requestMatchers("/app/projects/add/default").permitAll().anyRequest().authenticated())
				   .httpBasic(r->r.disable())
				   .formLogin(r->r.disable())
				   .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				   .build();
	}
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception
	{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
		           .build();
	}

}
