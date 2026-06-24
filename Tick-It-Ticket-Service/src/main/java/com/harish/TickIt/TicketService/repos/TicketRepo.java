package com.harish.TickIt.TicketService.repos;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.TicketService.enums.TicketPriority;
import com.harish.TickIt.TicketService.enums.TicketStatus;


@Repository
public interface TicketRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.TicketService.model.Ticket, Integer>
{
	List<com.harish.TickIt.TicketService.model.Ticket> findByProjectIdAndStatusNotAndAssignedToIsNull(int projectId, com.harish.TickIt.TicketService.enums.TicketStatus status);
	
	List<com.harish.TickIt.TicketService.model.Ticket> findByProjectIdAndStatusAndPriorityNotInAndAssignedToIsNull(int projectId, TicketStatus ts, List<TicketPriority> tp);
	
}
