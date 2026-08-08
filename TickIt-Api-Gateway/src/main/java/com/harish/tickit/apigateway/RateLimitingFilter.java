package com.harish.tickit.apigateway;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitingFilter extends org.springframework.web.filter.OncePerRequestFilter
{
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private org.springframework.data.redis.core.script.DefaultRedisScript<Long> redisScript;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String req= request.getRequestURI();
		String ip= request.getHeader("X-Forwarded-For");
		String id= request.getHeader("X-EmployeeId");
		
		if(ip==null || ip.isEmpty() || ip.equalsIgnoreCase("unknown"))
		{
			ip= request.getRemoteAddr();
		}
		
		String key= "rate_limit:"+id+":"+ip;
		long sec= Instant.now().getEpochSecond();
		long time= System.currentTimeMillis();
		long window= 600;
		
		long count=redisTemplate.execute(redisScript,Collections.singletonList(key),sec,window,time);
		
		if(count==0)
		{
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.getWriter().write("Rate limit exceeded. Please try again later.");
			return;
		}
		
		filterChain.doFilter(request, response);	
	}

}
