package com.harish.tickit.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig
{
	@Autowired
	private JwtAuthFilter auth;
	@Autowired
	private RateLimitingFilter rateLimit;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity sec) throws Exception
	{
		return sec.csrf(r->r.disable())
				  .formLogin(f->f.disable())
				  .httpBasic(b->b.disable())
				  .sessionManagement(r->r.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				  .authorizeHttpRequests(r->r.requestMatchers("/api/login", "/api/register","/oauth2/authorization/google", "/login/oauth2/code/google", "/favicon.ico").permitAll().anyRequest().authenticated())
				  .addFilterBefore(auth, UsernamePasswordAuthenticationFilter.class)
				  .addFilterAfter(rateLimit, JwtAuthFilter.class)
				  .build();
	}

}
