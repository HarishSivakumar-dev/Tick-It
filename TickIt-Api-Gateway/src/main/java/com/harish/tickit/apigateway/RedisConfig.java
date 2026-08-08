package com.harish.tickit.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

@Configuration
public class RedisConfig 
{
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) 
	{
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new org.springframework.data.redis.serializer.StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<>(Object.class));
		return template;
	}
	
	@Bean
	public DefaultRedisScript<Long> redisScript() 
	{
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setLocation(new org.springframework.core.io.ClassPathResource("scripts/redisScript.lua"));
		redisScript.setResultType(Long.class);
		return redisScript;
	}
	

}
