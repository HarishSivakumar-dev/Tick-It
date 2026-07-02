package com.harish.TickIt.UserService.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.harish.TickIt.UserService.enums.Gender;

public class ProfileResponseDto 
{
	private Long employeeId;
    private String userName;
    private String email;
    private String department;
    private String designation;
    private String contactNumber;
    private String profilePictureUrl;
    private String address;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String bio;
    private LocalDateTime profileCreatedAt;
    private LocalDateTime profileUpdatedAt;
    private Boolean profileCompleted;
    
    
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
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
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public LocalDateTime getProfileCreatedAt() {
		return profileCreatedAt;
	}
	public void setProfileCreatedAt(LocalDateTime profileCreatedAt) {
		this.profileCreatedAt = profileCreatedAt;
	}
	public LocalDateTime getProfileUpdatedAt() {
		return profileUpdatedAt;
	}
	public void setProfileUpdatedAt(LocalDateTime profileUpdatedAt) {
		this.profileUpdatedAt = profileUpdatedAt;
	}
	public Boolean getProfileCompleted() {
		return profileCompleted;
	}
	public void setProfileCompleted(Boolean profileCompleted) {
		this.profileCompleted = profileCompleted;
	}
		
}
