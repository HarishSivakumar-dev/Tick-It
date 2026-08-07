package com.harish.tickit.apigateway;

import java.io.IOException;
import java.time.Instant;
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
		Long rs= redisTemplate.opsForZSet().zCard(key);
		
		if(rs==0)
		{
			long sec= Instant.now().getEpochSecond();
			long time= System.currentTimeMillis();
			
			redisTemplate.opsForZSet().add(key,time,sec);
			redisTemplate.expire(key,10, java.util.concurrent.TimeUnit.MINUTES);
			
			filterChain.doFilter(request, response);
			return;
		}
		else
		{
			long sec= Instant.now().getEpochSecond();
			
			long rg= sec-600;
			redisTemplate.opsForZSet().removeRangeByScore(key,0,rg);
			
			Long ct=redisTemplate.opsForZSet().zCard(key)+1;
			
			if(ct>10)
			{
				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				response.getWriter().write("Rate limit exceeded. Please try again later.");
				return;
			}
			else
			{
				long time= System.currentTimeMillis();
				
				redisTemplate.opsForZSet().add(key,time,sec);
				redisTemplate.expire(key,10, java.util.concurrent.TimeUnit.MINUTES);
				
				filterChain.doFilter(request, response);
				return;
			}
		}
		
	}

}
