package com.harish.tickit.notificationService.dtos;

import com.harish.tickit.notificationService.enums.NotificationType;

public class NotificationDto
{
	private Long employeeId;
	private String title;
	private String message;
	private NotificationType type;
	
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		this.type = type;
	}

}
