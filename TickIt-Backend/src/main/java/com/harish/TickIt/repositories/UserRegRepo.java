package com.harish.TickIt.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRegRepo extends org.springframework.data.jpa.repository.JpaRepository<com.harish.TickIt.models.UserRegistration, Long>
{
	Optional<com.harish.TickIt.models.UserRegistration> findByUserName(String userName);
	Optional<com.harish.TickIt.models.UserRegistration> findByEmail(String email);

}
