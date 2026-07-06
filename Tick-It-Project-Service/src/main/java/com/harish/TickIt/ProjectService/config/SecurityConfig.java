package com.harish.TickIt.ProjectService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.harish.TickIt.ProjectService.auth.JwtAuthencationFilter;

@Component
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
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
	
	@Bean
	public RoleHierarchy roleHierarchy()
	{
		return  RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_LEAD > ROLE_USER");
	}
	
	@Bean 
	public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy)
	{
		org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler expressionHandler = new org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}

}
