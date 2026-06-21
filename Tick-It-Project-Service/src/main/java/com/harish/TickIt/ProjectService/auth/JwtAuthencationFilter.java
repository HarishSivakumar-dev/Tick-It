package com.harish.TickIt.ProjectService.auth;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.harish.TickIt.ProjectService.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthencationFilter extends org.springframework.web.filter.OncePerRequestFilter
{
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		if(request.getRequestURI().equals("/app/projects/add/default"))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer "))
		{
			String token = authHeader.substring(7);
			if (jwtUtil.validateToken(token))
			{
				String username = jwtUtil.getUsernameFromToken(token);
				List<String>roles = jwtUtil.getRolesFromToken(token);
				
				Set<SimpleGrantedAuthority> authorities = roles.stream()
													.map(SimpleGrantedAuthority::new)
													.collect(Collectors.toSet());
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				filterChain.doFilter(request, response);
				return;
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
	}

}
