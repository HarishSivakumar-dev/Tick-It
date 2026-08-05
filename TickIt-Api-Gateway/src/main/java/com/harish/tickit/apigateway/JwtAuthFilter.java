package com.harish.tickit.apigateway;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

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
				
				if(util.verifyJwt(token) && !redisTemplate.hasKey("blacklist:"+util.getUuidFromToken(token)))
				{
					List<SimpleGrantedAuthority> ls= util.getRoles(token)
							                             .stream()
							                             .map(r->{
							                            	 return new SimpleGrantedAuthority(r);
							                             })
							                             .toList();
					String user= util.getUserName(token);
					Integer id= util.getEmployeeId(token);
					
					UserPrincipal up= new UserPrincipal();
					up.setEmployeeId(id);
					up.setUserName(user);
					
			        UsernamePasswordAuthenticationToken auh= new UsernamePasswordAuthenticationToken(up, null, ls);
					SecurityContextHolder.getContext().setAuthentication(auh);
					filterChain.doFilter(request, response);
					return;
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

}
