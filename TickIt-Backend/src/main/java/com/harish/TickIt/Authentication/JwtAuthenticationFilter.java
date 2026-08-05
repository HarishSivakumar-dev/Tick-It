package com.harish.TickIt.Authentication;

import java.io.IOException;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.harish.TickIt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends org.springframework.web.filter.OncePerRequestFilter
{
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		
		String path = request.getRequestURI();
		System.out.println("Request Path: " + path);
		
		if(path.equals("/api/user/register") || path.equals("/api/user/login") || path.equals("/oauth2/authorization/google") || path.startsWith("/login/oauth2/code/google") || path.equals("/favicon.ico") || path.equals("/api/user/refresh"))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
			String token = authHeader.substring(7);
			if(jwtUtil.validateToken(token) && !redisTemplate.hasKey("blacklist:"+jwtUtil.getUuidFromToken(token)))
			{
				String username = jwtUtil.getUsernameFromToken(token);
				Set<SimpleGrantedAuthority> auh= jwtUtil.getRolesFromToken(token).stream()
																				  .map(r-> new SimpleGrantedAuthority(r))
																				  .collect(java.util.stream.Collectors.toSet());
				UserPrincipal up= new UserPrincipal();
				up.setEmployeeId(jwtUtil.getUserId(token));
				up.setUserName(username);
				
				org.springframework.security.core.Authentication auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(up, null, auh);
				org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
				filterChain.doFilter(request, response);
			}
			else
			{	
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "INVALID JWT");
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NO AUTH HEADER");
		}
	}

}
