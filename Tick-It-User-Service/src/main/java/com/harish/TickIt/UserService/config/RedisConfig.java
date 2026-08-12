package com.harish.TickIt.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.harish.TickIt.UserService.dtos.ProfileResponseDto;
import tools.jackson.databind.ObjectMapper;


@Configuration 
public class RedisConfig 
{
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper om) 
	{
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<>(om,Object.class));
		return template;
	}
	
	@Bean
	public RedisTemplate<String, ProfileResponseDto> redisTemplateUser(RedisConnectionFactory connectionFactory, ObjectMapper om)
	{
		RedisTemplate<String, ProfileResponseDto> template= new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<>(om,ProfileResponseDto.class));
		return template;
	}
	
	
	
}
