package com.harish.TickIt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception
	{
		return http.csrf(r->r.disable())
				   .authorizeHttpRequests(a->a.requestMatchers("/api/user/register", "/api/user/login", "/oauth2/authorization/**", "/login/oauth2/code/**" , "/favicon.ico/**").permitAll()
						   .anyRequest().authenticated())
				   .formLogin(r->r.disable())
				   .httpBasic(r->r.disable())
				   .exceptionHandling(
						   e->e.authenticationEntryPoint(new com.harish.TickIt.Exceptions.CustomAuthEntryPoint())
						   	   .accessDeniedHandler(new com.harish.TickIt.Exceptions.CustomAccessDeniedHandler())
						   
						   )
				   .authenticationProvider(custom)
				   .oauth2Login(o->o.successHandler(oauth2SuccessHandler))
				   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				   .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				   .build();
		
		
				
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				   .authenticationProvider(custom)
				   .build();
	}
	
	@Bean
	public RoleHierarchy roleHierarchy()
	{
		return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE>LEAD > ROLE_USER");
		
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy)
	{
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}
}
