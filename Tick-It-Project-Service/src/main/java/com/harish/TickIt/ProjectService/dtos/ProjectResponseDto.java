package com.harish.TickIt.ProjectService.dtos;

import java.time.LocalDate;
import com.harish.TicktIt.ProjectService.enums.ProjectStatus;

public class ProjectResponseDto 
{
	private long projectId;
	private String projectName;
	private String projectDescription;
	private long projectManagerId;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private boolean isActive;
	private LocalDate startDate;
	private LocalDate endDate;
	private ProjectStatus status;
	
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public long getProjectManagerId() {
		return projectManagerId;
	}
	public void setProjectManagerId(long projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public ProjectStatus getStatus() {
		return status;
	}
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

}
