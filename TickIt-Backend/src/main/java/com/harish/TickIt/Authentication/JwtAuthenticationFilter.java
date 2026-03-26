package com.harish.TickIt.Authentication;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import com.harish.TickIt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends org.springframework.web.filter.OncePerRequestFilter
{
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		
		String path = request.getRequestURI();
		if(path.equals("/api/register") || path.equals("/api/login"))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
			String token = authHeader.substring(7);
			if(jwtUtil.validateToken(token))
			{
				String username = jwtUtil.getUsernameFromToken(token);
				org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(username, null, java.util.Collections.emptyList());
				org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
				filterChain.doFilter(request, response);
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or expired token");
				return;
			}
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Missing or invalid Authorization header");
			return;
		}
	}

}
