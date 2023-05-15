package com.chibuisi.dailyinsightservice.springsecapp.repository;


import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	UserAccount findByUsername(String username);
	Optional<UserAccount> findUserAccountByEmail(String email);
	Optional<UserAccount> findUserAccountByUsername(String username);
	Boolean existsByUsername(String username);
}
