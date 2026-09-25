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
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

@Configuration
public class KafkaConfig
{
	@Bean
	public ProducerFactory<String, Object> producerFactory()
	{
		Map<String, Object> mp= new HashMap<String, Object>();
		
		mp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"http://localhost:19092");
		mp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		mp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(mp);
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf)
	{
		return new KafkaTemplate<String, Object>(pf);
	}

}
