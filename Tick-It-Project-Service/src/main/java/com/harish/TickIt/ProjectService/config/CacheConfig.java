package com.harish.TickIt.ProjectService.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import com.harish.TickIt.ProjectService.dtos.ProjectDetDto;
import com.harish.TickIt.ProjectService.dtos.ProjectResponseDto;
import com.harish.TickIt.ProjectService.dtos.UserDetailsDto;
import com.harish.TickIt.ProjectService.dtos.UserProjectDetailsDto;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class CacheConfig
{
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory factory, ObjectMapper om)
	{
		JavaType ser= om.getTypeFactory().constructCollectionLikeType(List.class, ProjectResponseDto.class);
		JavaType emp= om.getTypeFactory().constructCollectionType(List.class, UserDetailsDto.class);
		JavaType m= om.getTypeFactory().constructCollectionType(List.class, UserProjectDetailsDto.class);
		
		RedisCacheConfiguration json= RedisCacheConfiguration.defaultCacheConfig()
															 .entryTtl(Duration.ofMinutes(10))
															 .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(om, ser)));
		RedisCacheConfiguration empl= RedisCacheConfiguration.defaultCacheConfig()
															 .entryTtl(Duration.ofMinutes(10))
															 .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(om,emp)));
		RedisCacheConfiguration usr= RedisCacheConfiguration.defaultCacheConfig()
															.entryTtl(Duration.ofMinutes(10))
															.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(om,m)));
		RedisCacheConfiguration prj= RedisCacheConfiguration.defaultCacheConfig()
															.entryTtl(Duration.ofMinutes(10))
															.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(ProjectDetDto.class)));
	
		
		Map<String, RedisCacheConfiguration> mp= new HashMap<String, RedisCacheConfiguration>();
		mp.put("AllProjects", json);
		mp.put("ProjectMembersUnassigned", empl);
		mp.put("UserProjects", usr);
		mp.put("ProjectMembers", usr);
		mp.put("ProjectId", prj);
		
		return RedisCacheManager.builder(factory)
								.withInitialCacheConfigurations(mp)
								.build();			                
	}

}
