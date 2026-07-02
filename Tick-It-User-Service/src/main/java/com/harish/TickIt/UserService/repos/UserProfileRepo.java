package com.harish.TickIt.UserService.repos;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.harish.TickIt.UserService.models.UserProfile;

@Repository
public interface UserProfileRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.UserService.models.UserProfile, Long>
{
	Optional<UserProfile> findByEmail(String email);
	Optional<UserProfile> findByEmployeeId(Long employeeid);
	Optional<UserProfile> findByUserName(String username);
}
