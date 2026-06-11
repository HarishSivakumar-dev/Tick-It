package com.harish.TickIt.TicketService.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		// TODO Auto-generated method stub
		RequestAttributes ra =RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		
		String token=sra.getRequest().getHeader("Authorization");
		
		template.header("Authorization", token);
	}

}
