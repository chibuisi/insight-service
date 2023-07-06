package com.chibuisi.dailyinsightservice.springsecapp.service;

import com.chibuisi.dailyinsightservice.exception.UserAlreadyExistException;
import com.chibuisi.dailyinsightservice.springsecapp.model.AppRole;
import com.chibuisi.dailyinsightservice.springsecapp.model.CustomUserDetails;
import com.chibuisi.dailyinsightservice.springsecapp.model.Role;
import com.chibuisi.dailyinsightservice.springsecapp.model.UserAccount;
import com.chibuisi.dailyinsightservice.springsecapp.repository.UserAccountRepository;
import com.chibuisi.dailyinsightservice.springsecapp.user.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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

	public UserAccountDTO saveUserAccount(UserAccountDTO userAccountDTO) throws UserAlreadyExistException {
		// todo validate userAccount against null values
		Optional<UserAccount> existing = userRepository.findUserAccountByEmail(userAccountDTO.getEmail());
		if(!existing.isPresent())
			existing = userRepository.findUserAccountByUsername(userAccountDTO.getUsername());
		if(existing.isPresent())
			//todo handle this exception in a controller advice
			throw new UserAlreadyExistException("Email already exists");
		UserAccount userAccount = UserAccount.builder()
				.email(userAccountDTO.getEmail())
				.username(userAccountDTO.getUsername())
				.firstName(userAccountDTO.getFirstname())

				.lastName(userAccountDTO.getLastname())
				.password(passwordEncoder.encode(userAccountDTO.getPassword()))
				.agreedToEula(userAccountDTO.getAgreedToEula())
				.dateJoined(LocalDateTime.now())
				.ipAddress(userAccountDTO.getIpAddress())
				.timezone(userAccountDTO.getTimezone())
				.build();
		AppRole appRole = appRoleService.findAppRole(Role.ROLE_USER);
		if(appRole != null)
			userAccount.setRoles(Collections.singletonList(appRole));
		userRepository.save(userAccount);
		return userAccountDTO;
	}

	public Optional<UserAccountDTO> findUserByCredentials(String emailOrUsername){
		UserAccount userAccount;
		Optional<UserAccount> optionalExisting =getUserAccountFromEmailOrUsername(emailOrUsername);
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

	public Optional<UserAccount> getUserAccountFromEmailOrUsername(String emailOrUsername) {
		Optional<UserAccount> optionalExisting = userRepository
				.findUserAccountByEmail(emailOrUsername);
		if(!optionalExisting.isPresent())
			optionalExisting = userRepository
					.findUserAccountByUsername(emailOrUsername);
		return optionalExisting;
	}

	public Boolean checkUsernameAvailability(String username){
		return userRepository.existsByUsername(username);
	}

	public UserAccount saveUserAccount(UserAccount userAccount) {
		return userRepository.save(userAccount);
	}

	public List<UserAccount> findUsersById(List<Long> userIds) {
		return userRepository.findAllByIdIn(userIds);
	}
}
