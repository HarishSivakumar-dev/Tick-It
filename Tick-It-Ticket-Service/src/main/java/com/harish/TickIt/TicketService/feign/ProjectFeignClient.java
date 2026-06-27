package com.harish.TickIt.TicketService.feign;

import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.harish.TickIt.TicketService.dtos.TicketAvailDto;

@FeignClient
public interface ProjectFeignClient
{
	
	@PostMapping("/")
	Optional<TicketAvailDto> getProjectIdFromService(@RequestParam long projectId);
	
}
