package com.harish.TickIt.ProjectService;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.harish.TickIt.ProjectService.feign")
public class TickItProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickItProjectServiceApplication.class, args);
	}

}
