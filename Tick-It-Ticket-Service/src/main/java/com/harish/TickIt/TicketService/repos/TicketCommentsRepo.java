package com.harish.TickIt.TicketService.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.TickIt.TicketService.model.TicketComments;

public interface TicketCommentsRepo extends JpaRepository<TicketComments, Integer>
{

}
