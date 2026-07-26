package com.harish.TickIt.TicketService.dtos;

public class TicketCommentDto
{
	private String message;
	private Integer parentCommentId;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getParentCommentId() {
		return parentCommentId;
	}
	public void setParentCommentId(Integer parentCommentId) {
		this.parentCommentId = parentCommentId;
	}
}
