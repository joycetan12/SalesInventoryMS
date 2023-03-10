package com.perscholas.sims.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.perscholas.sims.model.User;
import com.perscholas.sims.security.UserRegistrationDto;

public interface UserService extends UserDetailsService {

	User findByEmail(String email);
	User save(UserRegistrationDto registration);

}
