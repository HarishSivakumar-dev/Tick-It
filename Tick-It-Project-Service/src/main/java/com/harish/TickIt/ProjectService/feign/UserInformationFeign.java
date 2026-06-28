package com.harish.TickIt.ProjectService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("Tick-It-User-Service")
public interface UserInformationFeign
{
	@GetMapping("/api/v1/user-profile/user/details")
	ResponseEntity<Boolean> findProjectManagerId(@RequestParam long projectManagerId);

}
