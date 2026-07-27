package com.harish.TickIt.TicketService.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.TicketService.auth.UserPrincipal;
import com.harish.TickIt.TicketService.dtos.TicketCommentDto;
import com.harish.TickIt.TicketService.dtos.TicketCommentResponseDto;
import com.harish.TickIt.TicketService.model.TicketComments;
import com.harish.TickIt.TicketService.repos.TicketCommentsRepo;

@Service
public class TicketCommentService
{
	@Autowired
	private TicketCommentsRepo tcr;
	
	public String createComment(int ticketId, TicketCommentDto dto)
	{
		UserPrincipal user= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		TicketComments tc= new TicketComments();
		tc.setCreatedAt(LocalDateTime.now());
		tc.setEmployeeId(user.getEmployeeId());
		tc.setUpdatedAt(null);
		tc.setTicketId(ticketId);
		tc.setEmployeeName(user.getUserName());
		tc.setMessage(dto.getMessage());

		if(dto.getParentCommentId()==null)
		{
			tc.setParentCommentId(null);
		}
		else
		{
			tc.setParentCommentId(dto.getParentCommentId());
		}
		
		tcr.save(tc);
		
		return "Comment posted Successfull";
	}
	
	public List<TicketCommentResponseDto> getCommentsForTicket(int ticketId)
	{
		List<TicketCommentResponseDto> res= tcr.findByTicketId(ticketId)
											   .stream()
											   .map(r->{
												   TicketCommentResponseDto dt= new TicketCommentResponseDto();
												   dt.setCreatedAt(r.getCreatedAt());
												   dt.setEmployeeId(r.getEmployeeId());
												   dt.setEmployeeName(r.getEmployeeName());
												   dt.setMessage(r.getMessage());
												   dt.setParentCommentId(r.getParentCommentId());
												   dt.setUpdatedAt(r.getUpdatedAt());
												   
												   return dt;
											   })
											   .toList();
		return res;
	}

}
