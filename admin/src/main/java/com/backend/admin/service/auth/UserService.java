package com.backend.admin.service.auth;

import com.backend.admin.dto.auth.ChangeRoleRequest;
import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.exception.NotFoundException;
import com.backend.admin.exception.ResourceConflictException;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.auth.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findById(ObjectId id) {
		return userRepository.findById(id).orElse(null);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

    public User save(User user){
        return userRepository.save(user);
    }

	public User create(UserRequest userRequest) {
		if (findByUsername(userRequest.getUsername()).isPresent())
			throw new ResourceConflictException(userRequest.getUsername(), "Username already exists");

		User u = new User();
		u.setUsername(userRequest.getUsername());
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		u.setEmail(userRequest.getEmail());
		u.setEnabled(true);

		u.setRoles(new ArrayList<>());

		for (String roleName : userRequest.getRoles())
			u.getRoles().addAll(roleService.findByName(roleName));

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

}