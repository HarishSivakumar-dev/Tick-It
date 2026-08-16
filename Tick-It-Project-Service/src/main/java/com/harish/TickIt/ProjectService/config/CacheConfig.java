package com.harish.TickIt.ProjectService.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import tools.jackson.databind.ObjectMapper;

@Configuration
public class CacheConfig
{
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory factory, ObjectMapper om)
	{
		GenericJacksonJsonRedisSerializer serializer= new GenericJacksonJsonRedisSerializer(om);
		
		RedisCacheConfiguration json= RedisCacheConfiguration.defaultCacheConfig()
															 .entryTtl(Duration.ofMinutes(10))
															 .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
		
		Map<String, RedisCacheConfiguration> mp= new HashMap<String, RedisCacheConfiguration>();
		mp.put("AllProjects", json);
		
		return RedisCacheManager.builder(factory)
								.withInitialCacheConfigurations(mp)
								.build();
				                
	}

}
