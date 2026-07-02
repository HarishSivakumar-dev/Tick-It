package com.harish.TickIt.UserService.repos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.harish.TickIt.UserService.models.UserProfile;

@Repository
public interface UserProfileRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.UserService.models.UserProfile, Long>
{
	Optional<UserProfile> findByEmail(String email);
	Optional<UserProfile> findByEmployeeId(Long employeeid);
	Optional<UserProfile> findByUserName(String username);
	List<UserProfile> findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String query, String query2);
	
	@Query("SELECT u FROM UserProfile u WHERE (:department IS NULL OR u.department = :department) AND (:designation IS NULL OR u.designation = :designation)")
	List<UserProfile> findBasedOnFilter(@Param(value="department") String department, @Param(value="designation") String designation);
}
