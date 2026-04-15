package com.harish.TickIt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.harish.TickIt.feign")
public class TickItApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickItApplication.class, args);
	}

}
