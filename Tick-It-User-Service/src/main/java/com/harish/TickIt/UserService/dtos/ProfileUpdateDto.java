package com.harish.TickIt.UserService.dtos;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;
import com.harish.TickIt.UserService.enums.Gender;

public class ProfileUpdateDto
{
    private String contactNumber;
    private MultipartFile profilePicture;
    private String address;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String bio;
    
    
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public MultipartFile getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
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


}
