package com.harish.TickIt.TicketService.auth;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtFeignRequestInterceptor implements RequestInterceptor
{

	@Override
	public void apply(RequestTemplate template) 
	{
		RequestAttributes request= RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sessionRequestAttributes = (ServletRequestAttributes) request;
		String authHeader = sessionRequestAttributes.getRequest().getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer "))
		{
			template.header("Authorization", authHeader);
		}
	}
	
}
