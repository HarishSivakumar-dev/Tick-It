package com.harish.TickIt.UserService.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserProfile 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String email;
	private Long employeeId;
	private String department;
	private String role;
	private String contactNumber;
	private String profilePictureUrl;
	private String address;
	private String dateOfBirth;
	private String gender;
	
	private Long noOfTicketsAssigned;
	private Long noOfTicketsResolved;
	private long noOfTicketsActionPending;
	private LocalDateTime lastLoginTime;
	private LocalDate accountCreationDate;
	private String accountStatus;
	private Long noOfTicketsExcalated;
	private Long strikeCount;
	private LocalDateTime lastStrikeDate;
	private String performanceRating;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getNoOfTicketsAssigned() {
		return noOfTicketsAssigned;
	}
	public void setNoOfTicketsAssigned(Long noOfTicketsAssigned) {
		this.noOfTicketsAssigned = noOfTicketsAssigned;
	}
	public Long getNoOfTicketsResolved() {
		return noOfTicketsResolved;
	}
	public void setNoOfTicketsResolved(Long noOfTicketsResolved) {
		this.noOfTicketsResolved = noOfTicketsResolved;
	}
	public LocalDateTime getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public LocalDate getAccountCreationDate() {
		return accountCreationDate;
	}
	public void setAccountCreationDate(LocalDate accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Long getNoOfTicketsExcalated() {
		return noOfTicketsExcalated;
	}
	public void setNoOfTicketsExcalated(Long noOfTicketsExcalated) {
		this.noOfTicketsExcalated = noOfTicketsExcalated;
	}
	public Long getStrikeCount() {
		return strikeCount;
	}
	public void setStrikeCount(Long strikeCount) {
		this.strikeCount = strikeCount;
	}
	public LocalDateTime getLastStrikeDate() {
		return lastStrikeDate;
	}
	public void setLastStrikeDate(LocalDateTime lastStrikeDate) {
		this.lastStrikeDate = lastStrikeDate;
	}
	public String getPerformanceRating() {
		return performanceRating;
	}
	public void setPerformanceRating(String performanceRating) {
		this.performanceRating = performanceRating;
	}
	public long getNoOfTicketsActionPending() {
		return noOfTicketsActionPending;
	}
	public void setNoOfTicketsActionPending(long noOfTicketsActionPending) {
		this.noOfTicketsActionPending = noOfTicketsActionPending;
	}
	

}
