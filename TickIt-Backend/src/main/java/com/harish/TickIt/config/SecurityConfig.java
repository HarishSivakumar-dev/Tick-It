package com.harish.TickIt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.harish.TickIt.Authentication.CustomAuthenticationProvider;
import com.harish.TickIt.Authentication.JwtAuthenticationFilter;
import com.harish.TickIt.Authentication.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig 
{
	@Autowired
	private CustomAuthenticationProvider custom;
	@Autowired
	private OAuth2SuccessHandler oauth2SuccessHandler;
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception
	{
		return http.csrf(r->r.disable())
				   .authorizeHttpRequests(a->a.requestMatchers("/api/register", "/api/login", "/oauth2/authorization/**", "/login/oauth2/code/**").permitAll()
						   .anyRequest().authenticated())
				   .formLogin(r->r.disable())
				   .httpBasic(r->r.disable())
				   .authenticationProvider(custom)
				   .oauth2Login(o->o.successHandler(oauth2SuccessHandler))
				   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				   .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				   .build();
		
		
				
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				   .authenticationProvider(custom)
				   .build();
	}
}
