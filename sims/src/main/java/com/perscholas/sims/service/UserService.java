package com.perscholas.sims.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.perscholas.sims.dto.UserRegistrationDto;
import com.perscholas.sims.model.User;

public interface UserService extends UserDetailsService {

	User findByEmail(String email);
	User save(UserRegistrationDto registration);

}
