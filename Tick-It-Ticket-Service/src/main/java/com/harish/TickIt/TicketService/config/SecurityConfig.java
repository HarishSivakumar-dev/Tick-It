package com.harish.TickIt.TicketService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.harish.TickIt.TicketService.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		return http.csrf(r->r.disable())
					.authorizeHttpRequests(r->r.requestMatchers("/**").permitAll())
					.httpBasic(t->t.disable())
					.formLogin(t->t.disable())
					.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
					.build();
	}
	
	@Bean
	public org.springframework.security.authentication.AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				   .build();
	}
	
	@Bean
	public org.springframework.security.access.hierarchicalroles.RoleHierarchy roleHierarchy()
	{
		return org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_LEAD > ROLE_USER");
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public org.springframework.security.access.expression.method.MethodSecurityExpressionHandler methodSecurityExpressionHandler(org.springframework.security.access.hierarchicalroles.RoleHierarchy roleHierarchy)
	{
		org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler expressionHandler = new org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}


}
