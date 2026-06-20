package com.harish.TickIt.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.harish.TickIt.UserService.feign")
public class TickItUserServiceApplication
{

	public static void main(String[] args) {
		SpringApplication.run(TickItUserServiceApplication.class, args);
	}

}
