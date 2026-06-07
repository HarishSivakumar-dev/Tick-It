package com.harish.TickIt.UserService.config;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import feign.RequestTemplate;

public class JwtFeignRequestInterceptor implements feign.RequestInterceptor
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
