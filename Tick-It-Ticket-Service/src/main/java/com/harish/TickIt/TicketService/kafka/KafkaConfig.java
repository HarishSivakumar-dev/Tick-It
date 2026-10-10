package com.harish.TickIt.TicketService.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.mapping.DefaultJacksonJavaTypeMapper;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import com.harish.TickIt.TicketService.kafka.events.TicketActionEvent;

@Configuration
public class KafkaConfig
{
	@Bean
	public ProducerFactory<String, Object> producerFactory()
	{
		Map<String, Object> mp= new HashMap<String, Object>();
		
		DefaultJacksonJavaTypeMapper typeMapper= new DefaultJacksonJavaTypeMapper();
		typeMapper.setIdClassMapping(Map.of("TicketActionEvent", TicketActionEvent.class));
		
		JacksonJsonSerializer<Object> serializer= new JacksonJsonSerializer<>();
		serializer.setTypeMapper(typeMapper);
		
		mp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:19092");
		
		return new DefaultKafkaProducerFactory<>(mp,new StringSerializer(), serializer);
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf)
	{
		return new KafkaTemplate<String, Object>(pf);
	}

}
