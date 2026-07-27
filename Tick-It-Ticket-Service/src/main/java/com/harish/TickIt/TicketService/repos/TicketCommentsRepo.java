package com.harish.TickIt.TicketService.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.TicketService.model.TicketComments;

@Repository
public interface TicketCommentsRepo extends JpaRepository<TicketComments, Integer>
{
	List<TicketComments> findByTicketId(int ticketId);
}
