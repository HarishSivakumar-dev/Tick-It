package com.harish.TickIt.TicketService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.harish.TickIt.TicketService.dtos.UserDetailsDto;

@FeignClient("Tick-It-User-Service")
public interface UserProfileFeignClient
{
	@GetMapping("/api/v1/user-profile/getProfile")
	public ResponseEntity<UserDetailsDto> getUserProfile();

}
