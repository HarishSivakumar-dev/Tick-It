package com.harish.TickIt.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.harish.TickIt.dtos.UserDetailsDto;

@FeignClient("Tick-It-Project-Service")
public interface ProjectServiceFeign 
{
	@PostMapping("/app/projects/add/default")
	public ResponseEntity<String> saveUserDetails(@RequestBody UserDetailsDto dto);
}
