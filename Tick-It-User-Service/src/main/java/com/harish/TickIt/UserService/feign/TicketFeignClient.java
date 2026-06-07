package com.harish.TickIt.UserService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.harish.TickIt.UserService.dtos.TicketDetailsDto;

@FeignClient(name = "user-profile-service")
public interface TicketFeignClient
{
	@PostMapping("")
	public ResponseEntity<String> ticketCreation(@RequestBody TicketDetailsDto dto);

}
