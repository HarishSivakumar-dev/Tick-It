package com.harish.TickIt.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.harish.TickIt.UserService.authentication.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	@Autowired
	private JwtAuthenticationFilter jwt;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		return http.csrf(r->r.disable())
				   .formLogin(f -> f.disable())
				   .httpBasic(b -> b.disable())
				   .authorizeHttpRequests(r->r.requestMatchers("/api/v1/user-profile/createProfile").permitAll().anyRequest().authenticated())
				   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				   .addFilterBefore(jwt, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
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
	
	@Bean
	public org.springframework.security.access.expression.method.MethodSecurityExpressionHandler methodSecurityExpressionHandler(org.springframework.security.access.hierarchicalroles.RoleHierarchy roleHierarchy)
	{
		org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler expressionHandler = new org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}
}
