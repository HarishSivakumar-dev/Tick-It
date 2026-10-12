package com.harish.TickIt.TicketService.dtos;

import java.time.LocalDateTime;

public class TicketCommentPayloadDto
{
	private int ticketId;
	private Long projectId;
	private int commentId;
	private String message;
	private LocalDateTime createdAt;
	private Long employeeId;
	private String employeeName;
	
	
	public TicketCommentPayloadDto(int ticketId, Long projectId, int commentId, String message, LocalDateTime createdAt,
			Long employeeId, String employeeName) {
		super();
		this.ticketId = ticketId;
		this.projectId = projectId;
		this.commentId = commentId;
		this.message = message;
		this.createdAt = createdAt;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
