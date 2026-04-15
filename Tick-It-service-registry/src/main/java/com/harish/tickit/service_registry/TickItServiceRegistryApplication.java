package com.harish.tickit.service_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TickItServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickItServiceRegistryApplication.class, args);
	}

}
