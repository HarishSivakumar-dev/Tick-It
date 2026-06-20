package com.harish.TickIt.TicketService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.harish.TickIt.TicketService.feign")
public class TickItTicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickItTicketServiceApplication.class, args);
	}

}
