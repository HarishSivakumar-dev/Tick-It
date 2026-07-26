package com.harish.TickIt.TicketService.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.TicketService.auth.UserPrincipal;
import com.harish.TickIt.TicketService.dtos.TicketCommentDto;
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

}
