package com.harish.TickIt.UserService.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.harish.TickIt.UserService.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profiles")
public class UserProfile 
{	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private Long employeeId;

	    @Column(nullable = false)
	    private String userName;
	    
	    @Column(nullable = false, unique = true)
	    private String email;

	    private String department;

	    private String designation;
	    
	    private String contactNumber;
	    private String profilePictureUrl;
	    private String address;
	    private LocalDate dateOfBirth;
	    
	    @Enumerated(EnumType.STRING)
	    private Gender gender;
	    
	    @Column(length = 1000)
	    private String bio;
	    
	    private LocalDateTime profileCreatedAt;
	    private LocalDateTime profileUpdatedAt;
	    private Boolean profileCompleted;
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
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
	
	
