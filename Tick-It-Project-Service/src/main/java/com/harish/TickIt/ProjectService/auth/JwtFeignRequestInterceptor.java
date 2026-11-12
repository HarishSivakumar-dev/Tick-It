package com.harish.TickIt.ProjectService.auth;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtFeignRequestInterceptor implements RequestInterceptor
{

	@Override
	public void apply(RequestTemplate template)
	{
		// TODO Auto-generated method stub
		
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sessionRequestAttributes = (ServletRequestAttributes) requestAttributes;
		String authHeader = sessionRequestAttributes.getRequest().getHeader("Authorization");
		
		System.out.println("Authorization Header in Feign Interceptor: " + authHeader);
		if (authHeader != null && authHeader.startsWith("Bearer "))
		{
			template.header("Authorization", authHeader);
		}
			
	}

}
