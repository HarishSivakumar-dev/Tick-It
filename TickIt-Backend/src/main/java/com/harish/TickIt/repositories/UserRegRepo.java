package com.harish.TickIt.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.harish.TickIt.models.UserRegistration;


@Repository
public interface UserRegRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.models.UserRegistration, Long>
{
	Optional<com.harish.TickIt.models.UserRegistration> findByUserName(String userName);
	Optional<com.harish.TickIt.models.UserRegistration> findByEmail(String email);
	Optional<UserRegistration> findByEmployeeId(Long userId);

}
