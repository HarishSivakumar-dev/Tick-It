package com.harish.TickIt.TicketService.repos;

import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.TicketService.model.Ticket, Integer>
{
	List<com.harish.TickIt.TicketService.model.Ticket> findByProjectIdAndStatusNotAndAssignedToIsNull(int projectId, com.harish.TickIt.TicketService.enums.TicketStatus status);
	
}
