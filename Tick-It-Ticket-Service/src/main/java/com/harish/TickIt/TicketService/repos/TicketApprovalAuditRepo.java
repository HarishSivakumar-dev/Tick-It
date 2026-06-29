package com.harish.TickIt.TicketService.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harish.TickIt.TicketService.model.TicketApprovalAudit;

public interface TicketApprovalAuditRepo extends JpaRepository<TicketApprovalAudit, Integer>
{

}
