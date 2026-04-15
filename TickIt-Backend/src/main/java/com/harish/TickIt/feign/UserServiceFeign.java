package com.harish.TickIt.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.harish.TickIt.dtos.UserProfileDto;

@FeignClient("Tick-It-User-Service")
public interface UserServiceFeign
{
	@PostMapping("/api/v1/user-profile/createProfile")
	public ResponseEntity<String> createUserProfile(@RequestBody UserProfileDto dto);

}
