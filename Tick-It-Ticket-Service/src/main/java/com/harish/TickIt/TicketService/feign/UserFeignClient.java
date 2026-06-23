package com.harish.TickIt.TicketService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.harish.TickIt.TicketService.dtos.UserFeignDto;

@FeignClient("")
public interface UserFeignClient
{
	@GetMapping("")
	ResponseEntity<UserFeignDto> getUserFromAuthService(@PathVariable Long userId);

}
