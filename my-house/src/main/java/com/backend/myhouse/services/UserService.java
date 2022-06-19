package com.backend.myhouse.services;

import com.backend.myhouse.exception.BadRequestException;
import com.backend.myhouse.model.auth.User;
import com.backend.myhouse.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

    @Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> optionalUser = findByUsername(username);
		return optionalUser.orElseThrow(() ->
				new BadRequestException(String.format("No user found with username '%s'.", username)));
	}
}