package com.harish.tickit.apigateway;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter extends org.springframework.web.filter.OncePerRequestFilter
{
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private org.springframework.data.redis.core.script.DefaultRedisScript<Long> redisScript;
	
	private static final Map<String, Integer> rq= Map.of(
			"/api/register", 10,
			"/api/login", 10,
			"/oauth2/authorization/google", 10,
			"/login/oauth2/code/google", 20
	);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String req= request.getRequestURI();
		String ip= request.getHeader("X-Forwarded-For");
		String key;
		
		if(!rq.containsKey(req))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		if(ip==null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
		{
			ip= request.getRemoteAddr();
		}
		
		if(SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserPrincipal)
		{
			UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String id= String.valueOf(up.getEmployeeId());
			key = "rate_limit:" + req + ":" + id + ":" + ip;
		}
		else
		{
			key = "rate_limit:" + req + ":" + ip;
		}
		
		
		long sec= Instant.now().getEpochSecond();
		long time= System.currentTimeMillis();
		long window= 600;
		int limit= rq.get(req);
		
		long count=redisTemplate.execute(redisScript,Collections.singletonList(key),sec,window,time, limit);
		
		if(count==0)
		{
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.getWriter().write("Rate limit exceeded. Please try again later.");
			return;
		}
		
		filterChain.doFilter(request, response);
		return;
	}

}
