package com.harish.TickIt.UserService.authentication;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import com.harish.TickIt.UserService.util.JwtUtil;
import jakarta.servlet.FilterChain;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends org.springframework.web.filter.OncePerRequestFilter
{
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String path = request.getRequestURI();
		if(path.equals("/api/v1/user-profile/createProfile"))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
			String token = authHeader.substring(7);
			try
			{
				if(jwtUtil.validateToken(token))
				{
					
					java.util.Set<org.springframework.security.core.authority.SimpleGrantedAuthority> auh= jwtUtil.getRolesFromToken(token).stream()
																						  .map(r-> new org.springframework.security.core.authority.SimpleGrantedAuthority(r))
																						  .collect(java.util.stream.Collectors.toSet());
					String username = jwtUtil.getUsernameFromToken(token);
					Long id= jwtUtil.getEmployeeId(token);
					
					UserPrincipal up= new UserPrincipal();
					up.setEmployeeId(id);
					up.setUserName(username);
					
					org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(up, null, auh);
					org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
					filterChain.doFilter(request, response);
				}
				else
				{
					throw new org.springframework.security.authentication.BadCredentialsException("Invalid JWT token");
				}
			}
			catch(Exception e)
			{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Unauthorized: " + e.getMessage());
			}
			
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Unauthorized: Missing or invalid Authorization header");
		}
	}

}
