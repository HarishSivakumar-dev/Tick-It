package com.harish.tickit.apigateway;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter 
{
	@Autowired
	private JwtUtil util;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String s= request.getRequestURI();
		
		if(s.equals("/api/register") || s.equals("/api/login") || s.startsWith("/oauth2/authorization/google") || s.startsWith("/login/oauth2/code/google") || s.startsWith("/favicon.ico"))
		{
			System.out.println("Inside api gateway");
			filterChain.doFilter(request, response);
		}
		else
		{
			System.out.println("Auth gateway");
			String auth= request.getHeader("Authorization");
			if(auth!=null && !auth.isBlank() && auth.startsWith("Bearer "))
			{
				String token= auth.substring(7);
				
				if(util.verifyJwt(token))
				{
					List<SimpleGrantedAuthority> ls= util.getRoles(token)
							                             .stream()
							                             .map(r->{
							                            	 return new SimpleGrantedAuthority(r);
							                             })
							                             .toList();
					String user= util.getUserName(token);
			        UsernamePasswordAuthenticationToken auh= new UsernamePasswordAuthenticationToken(user, null, ls);
					SecurityContextHolder.getContext().setAuthentication(auh);
					filterChain.doFilter(request, response);
					return;
				}
				else
				{
					throw new RuntimeException("INVALID JWT TOKEN ");
				}
			}
			else
			{
				throw new RuntimeException("NO AUTH HEADER !");
			}
		}
	}

}
