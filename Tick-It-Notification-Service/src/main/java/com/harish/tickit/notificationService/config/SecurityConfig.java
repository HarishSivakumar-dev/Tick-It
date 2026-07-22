package com.harish.tickit.notificationService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.harish.tickit.notificationService.auth.JwtFilter;

@Configuration
public class SecurityConfig
{
	@Autowired
	private JwtFilter jwtfilter;
	
	@Bean
	public SecurityFilterChain secConfig(HttpSecurity sec)
	{
		return sec.csrf(r->r.disable())
				  .httpBasic(r->r.disable())
				  .formLogin(r->r.disable())
				  .sessionManagement(r->r.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				  .authorizeHttpRequests(r->r.requestMatchers(" ").permitAll().anyRequest().authenticated())
				  .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)
				  .build();
	}

}
