package com.harish.TickIt.TicketService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.harish.TickIt.TicketService.dtos.TicketAvailDto;

@FeignClient("Tick-It-Project-Service")
public interface ProjectFeignClient
{
	@GetMapping("/app/project/find")
	TicketAvailDto getProjectIdFromService(@RequestParam long projectId);
	
	@GetMapping("app/projects/get/{projectId}")
	ResponseEntity<Boolean> verifyEmployee(@PathVariable long projectId);
	
}
