package com.backend.admin.service.auth;

import com.backend.admin.dto.auth.ChangeRoleRequest;
import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.exception.BadRequestException;
import com.backend.admin.exception.NotFoundException;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.auth.UserRepository;
import com.backend.admin.service.EmailService;
import com.backend.admin.util.PasswordGenerator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private EmailService emailService;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findById(ObjectId id) {
		return userRepository.findById(id).orElse(null);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

    public User save(User user) {
        return userRepository.save(user);
    }

	public User create(UserRequest userRequest) {
		String generatedUsername;
		do {
			generatedUsername = generateUsername(userRequest);
		} while (findByUsername(generatedUsername).isPresent());
		String generatedPassword = PasswordGenerator.generatePassword(8);

		User u = new User();
		u.setUsername(generatedUsername);
		u.setPassword(passwordEncoder.encode(generatedPassword));
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		u.setEmail(userRequest.getEmail());
		u.setEnabled(true);
		u.setRoles(new ArrayList<>());

		for (String roleName : userRequest.getRoles())
			u.getRoles().addAll(roleService.findByName(roleName));

		u = userRepository.save(u);
		emailService.sendEmail(u.getEmail(), "Smart home registration",
				"Your username: "+u.getUsername()+"\nYour password: "+generatedPassword
		);

		return userRepository.save(u);
	}

	public User changeRole(ChangeRoleRequest changeRoleRequest) {
		User user = userRepository.findById(changeRoleRequest.getUserId())
				.orElseThrow(() -> new NotFoundException("User not found"));
		user.setRoles(new ArrayList<>());

		for (String roleName : changeRoleRequest.getRoles())
			user.getRoles().addAll(roleService.findByName(roleName));

		return userRepository.save(user);
	}

    @Override
	public UserDetails loadUserByUsername(String username) throws BadRequestException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isEmpty()) {
			throw new BadRequestException(String.format("No user found with username '%s'.", username));
		} else {
			return optionalUser.get();
		}
	}

	private String generateUsername(UserRequest user) {
		return user.getFirstName().replace(" ", "") + '.' +
				user.getLastName().replace(" ", "") +
				PasswordGenerator.generateNumberSequence(4);
	}

}