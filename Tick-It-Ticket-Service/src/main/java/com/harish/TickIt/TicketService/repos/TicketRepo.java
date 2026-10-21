package com.harish.TickIt.TicketService.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.TicketService.enums.TicketApprovalStatus;
import com.harish.TickIt.TicketService.enums.TicketPriority;
import com.harish.TickIt.TicketService.enums.TicketStatus;
import com.harish.TickIt.TicketService.model.Ticket;


@Repository
public interface TicketRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.TicketService.model.Ticket, Integer>
{
	List<Ticket> findByProjectIdAndStatusAndAssignedToIsNull(int projectId, com.harish.TickIt.TicketService.enums.TicketStatus status);
	
	List<Ticket> findByProjectIdAndStatusInAndPriorityNotInAndAssignedToIsNull(int projectId, List<TicketStatus> ts, List<TicketPriority> tp);
	
	@Query("""
			SELECT t from Ticket t
			WHERE (:status IS NULL OR t.status =:status)
			AND
			(:priority IS NULL OR t.priority =:priority)
			AND
			(:approval IS NULL OR t.approved =:approval)
			AND
			t.projectId =:projectId
			
			""")
	List<Ticket> filterBySpecs(@Param("status") TicketStatus status, @Param("priority") TicketPriority priority, @Param("approval") TicketApprovalStatus approval, @Param("projectId") Long projectId);

	List<Ticket> findByAssignedEmployeeIdOrderByCreatedAtDesc(long employeeId);
	
	List<Ticket> findByProjectId(int projectId);
	
	List<Ticket> findByProjectIdAndApproved(int projectId, TicketApprovalStatus approval);
		
	Optional<Ticket> findByIdAndApproved(int ticketId, TicketApprovalStatus approval);
}
