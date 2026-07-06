package com.harish.TickIt.TicketService.feign;

import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.harish.TickIt.TicketService.dtos.TicketAvailDto;

@FeignClient("Tick-It-Project-Service")
public interface ProjectFeignClient
{
	@GetMapping("/app/project/find")
	Optional<TicketAvailDto> getProjectIdFromService(@RequestParam long projectId);
	
}
