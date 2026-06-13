package com.harish.TickIt.TicketService.auth;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.harish.TickIt.TicketService.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String authHeader = request.getHeader("Authorization");
		if(authHeader !=null && authHeader.startsWith("Bearer "))
		{
			String token = authHeader.substring(7);
			
			if(token !=null && jwtUtil.validateToken(token))
			{
				List<String> roles= jwtUtil.getRolesFromToken(token);
				Set<org.springframework.security.core.authority.SimpleGrantedAuthority> authorities= roles.stream()
																											.map(r->new org.springframework.security.core.authority.SimpleGrantedAuthority(r))
																											.collect(java.util.stream.Collectors.toSet());
				
				Authentication authentication= new UsernamePasswordAuthenticationToken(jwtUtil.getUsernameFromToken(token), null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
				return;
			}
			else
			{
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
				return;
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or Invalid Authorization Header");
			return;
		}
		
	}
	
}
