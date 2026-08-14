package com.harish.TickIt.TicketService.repos;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.TicketService.enums.TicketApprovalStatus;
import com.harish.TickIt.TicketService.enums.TicketPriority;
import com.harish.TickIt.TicketService.enums.TicketStatus;


@Repository
public interface TicketRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.TicketService.model.Ticket, Integer>
{
	List<com.harish.TickIt.TicketService.model.Ticket> findByProjectIdAndStatusNotAndAssignedToIsNull(int projectId, com.harish.TickIt.TicketService.enums.TicketStatus status);
	
	List<com.harish.TickIt.TicketService.model.Ticket> findByProjectIdAndStatusInAndPriorityNotInAndAssignedToIsNull(int projectId, List<TicketStatus> ts, List<TicketPriority> tp);
	
	@Query("""
			SELECT t from Ticket t
			WHERE (:status IS NULL OR t.status =:status)
			AND
			(:priority IS NULL OR t.priority =:priority)
			AND
			(:approval IS NULL OR t.approved =:approval)
			
			""")
	List<com.harish.TickIt.TicketService.model.Ticket> filterBySpecs(@Param("status") TicketStatus status, @Param("priority") TicketPriority priority, @Param("approval") TicketApprovalStatus approval);

	List<com.harish.TickIt.TicketService.model.Ticket> findByAssignedEmployeeIdOrderByCreatedAtDesc(long employeeId);
	
	List<com.harish.TickIt.TicketService.model.Ticket> findByProjectId(int projectId);
	
	List<com.harish.TickIt.TicketService.model.Ticket> findByProjectIdAndApproved(int projectId, TicketApprovalStatus approval);
	
}
