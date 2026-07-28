package com.harish.TickIt.TicketService.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.TicketService.auth.UserPrincipal;
import com.harish.TickIt.TicketService.dtos.TicketCommentDto;
import com.harish.TickIt.TicketService.dtos.TicketCommentResponseDto;
import com.harish.TickIt.TicketService.model.TicketComments;
import com.harish.TickIt.TicketService.repos.TicketCommentsRepo;
import jakarta.transaction.Transactional;

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
		tc.setDeleted(Boolean.FALSE);

		if(dto.getParentCommentId()==null)
		{
			tc.setParentCommentId(null);
		}
		else
		{
			Optional<TicketComments> res= tcr.findById(dto.getParentCommentId());
			if(res.isPresent())
			{
				tc.setParentCommentId(dto.getParentCommentId());
			}
			else
			{
				throw new RuntimeException("NO TICKET FOUND");
			}
		}
		
		tcr.save(tc);
		
		return "Comment posted Successfull";
	}
	
	public List<TicketCommentResponseDto> getCommentsForTicket(int ticketId)
	{
		List<TicketCommentResponseDto> res= tcr.findByTicketIdAndDeletedFalseOrderByCreatedAtAsc(ticketId)
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

	@Transactional
	public String updateComment(int commentId, String message) 
	{
		TicketComments tc= tcr.findById(commentId).orElseThrow(()-> new RuntimeException("NO COMMENT FOUND"));
		UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if((tc.getEmployeeId().equals(up.getEmployeeId())) && tc.getDeleted().equals(Boolean.FALSE))
		{
			tc.setMessage(message);
			tc.setUpdatedAt(LocalDateTime.now());
		}
		else
		{
			throw new RuntimeException("This action is not allowed");
		}
		
		return "Comment updated";
	}
	
	public String softDeleteEmployeeComment(int commentId)
	{
		TicketComments tc= tcr.findById(commentId).orElseThrow(()-> new RuntimeException("NO COMMENT FOUND"));
		UserPrincipal up= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if((tc.getEmployeeId().equals(up.getEmployeeId())) && tc.getDeleted().equals(Boolean.FALSE))
		{
			tc.setDeleted(Boolean.TRUE);
			tc.setUpdatedAt(LocalDateTime.now());
		}
		else
		{
			throw new RuntimeException("This action is not allowed");
		}
		
		return "Comment updated";
	}
	

}
