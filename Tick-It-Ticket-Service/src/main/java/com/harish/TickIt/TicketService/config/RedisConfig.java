package com.harish.TickIt.TicketService.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.harish.TickIt.TicketService.dtos.TicketResponseDto;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JavaType;

@Configuration
public class RedisConfig
{
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper mapper)
	{
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<>(mapper,Object.class));
		return template;
	}
	
	@Bean
	public RedisTemplate<String, List<TicketResponseDto>> redisTemplateTickets(RedisConnectionFactory connectionFactory, ObjectMapper mapper)
	{
		JavaType tp= mapper.getTypeFactory().constructCollectionType(List.class, TicketResponseDto.class);
		RedisTemplate<String, List<TicketResponseDto>> template = new RedisTemplate<>();
		
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<>(mapper, tp));
		
		return template;
	}
	
}
