package com.chibuisi.dailyinsightservice.springsecapp.service;

import com.chibuisi.dailyinsightservice.springsecapp.model.AppRole;
import com.chibuisi.dailyinsightservice.springsecapp.model.CustomUserDetails;
import com.chibuisi.dailyinsightservice.springsecapp.model.Role;
import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import com.chibuisi.dailyinsightservice.springsecapp.repository.UserAccountRepository;
import com.chibuisi.dailyinsightservice.springsecapp.user.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class MyUserDetailsService implements UserDetailsService {
	private Logger log = Logger.getLogger("HomeResource");
	@Autowired
	private UserAccountRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AppRoleService appRoleService;

	@Override
	public CustomUserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		Optional<UserAccount> optionalUserAccount;

		optionalUserAccount = userRepository.findUserAccountByUsername(usernameOrEmail);
		if(!optionalUserAccount.isPresent())
			optionalUserAccount = userRepository.findUserAccountByEmail(usernameOrEmail);
		if(!optionalUserAccount.isPresent())
			throw new UsernameNotFoundException("User " + usernameOrEmail + " not Found");
		UserAccount userAccount = optionalUserAccount.get();

	    return CustomUserDetails.builder()
	        .username(usernameOrEmail)
	        .password(userAccount.getPassword())
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.email(userAccount.getEmail())
				.id(userAccount.getId().toString())
	        .authorities(userAccount.getRoles())
	        .build();
	}

	public UserAccountDTO saveUserAccount(UserAccountDTO userAccountDTO){
		// todo validate userAccount against null values
		Optional<UserAccount> existing = userRepository.findUserAccountByEmail(userAccountDTO.getEmail());
		if(!existing.isPresent())
			existing = userRepository.findUserAccountByUsername(userAccountDTO.getUsername());
		if(existing.isPresent())
			//todo handle this exception in a controller advice
			throw new RuntimeException("User Already Exists");
		UserAccount userAccount = UserAccount.builder()
				.email(userAccountDTO.getEmail())
				.username(userAccountDTO.getUsername())
				.firstName(userAccountDTO.getFirstname())
				.lastName(userAccountDTO.getLastname())
				.password(passwordEncoder.encode(userAccountDTO.getPassword()))
				.build();
		AppRole appRole = appRoleService.findAppRole(Role.ROLE_USER);
		if(appRole != null)
			userAccount.setRoles(Collections.singletonList(appRole));
		userRepository.save(userAccount);
		return userAccountDTO;
	}

	public Optional<UserAccountDTO> findUserByCredentials(String emailOrEmail){
		UserAccount userAccount;
		Optional<UserAccount> optionalExisting = userRepository
				.findUserAccountByEmail(emailOrEmail);
		if(!optionalExisting.isPresent())
			optionalExisting = userRepository
					.findUserAccountByUsername(emailOrEmail);
		if (!optionalExisting.isPresent())
			return Optional.empty();
		userAccount = optionalExisting.get();
		UserAccountDTO userAccountDTO = UserAccountDTO.builder()
				.email(userAccount.getEmail())
				.username(userAccount.getUsername())
				.firstname(userAccount.getFirstName())
				.lastname(userAccount.getLastName()).build();
		return Optional.of(userAccountDTO);
	}
}
