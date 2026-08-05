package com.harish.tickit.notificationService.auth;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.harish.tickit.notificationService.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter
{
	@Autowired
	private JwtUtil util;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String head= request.getHeader("Authorization");
		if(head==null || head.isBlank() || head.isEmpty())
		{
			throw new RuntimeException("NO AUTH HEADER ");
		}
		else
		{
			if(head.startsWith("Bearer") && head.substring(7)!=null)
			{
				String token= util.getUsername(head);
				if(util.validateToken(token) && !redisTemplate.hasKey("blacklist:"+util.getUuidFromToken(token)))
				{
					List<String> ls= util.getRolesFromToken(token);
					
					List<SimpleGrantedAuthority> sga= ls.stream()
							                            .map(r->{
							                            	SimpleGrantedAuthority sg= new SimpleGrantedAuthority(r);
							                            	return sg;
							                            })
							                            .toList();
					UserPrincipal usr= new UserPrincipal();
					usr.setEmployeeId(util.extractUserId(token));
					usr.setUserName(util.getUsername(token));
					
					Authentication auth= new UsernamePasswordAuthenticationToken(usr, null, sga);
					SecurityContextHolder.getContext().setAuthentication(auth);
					
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
				throw new RuntimeException("NO BEARER TOKEN");
			}
		}
		
	}

}
