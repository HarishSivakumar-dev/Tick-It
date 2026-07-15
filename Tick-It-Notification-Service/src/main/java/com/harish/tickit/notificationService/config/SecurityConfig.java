package com.harish.tickit.notificationService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity sec)
	{
		return sec.csrf(r->r.disable())
				  .httpBasic(r->r.disable())
				  .formLogin(r->r.disable())
				  .sessionManagement(r->r.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				  .authorizeHttpRequests(r->r.requestMatchers(" ").permitAll().anyRequest().authenticated())
				  .build();
	}

}
