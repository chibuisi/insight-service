package com.chibuisi.dailyinsightservice.springsecapp.repository;


import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	public UserAccount findByUsername(String username);
	public Optional<UserAccount> findUserAccountByEmail(String email);
	public Optional<UserAccount> findUserAccountByUsername(String username);
}
