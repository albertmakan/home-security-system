package com.backend.admin.service.auth;

import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.exception.BadRequestException;
import com.backend.admin.exception.ResourceConflictException;
import com.backend.admin.model.auth.Role;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.auth.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public User findById(ObjectId id) throws AccessDeniedException {
		return userRepository.findById(id).orElse(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

    public User save(User user){
        return userRepository.save(user);
    }

	public User create(UserRequest userRequest) {
		if (findByUsername(userRequest.getUsername()).isPresent())
			throw new ResourceConflictException(userRequest.getUsername(), "Username already exists");

        String passRegex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        Pattern pattern = Pattern.compile(passRegex);
        Matcher matcher = pattern.matcher(userRequest.getPassword());

        if (!matcher.matches())
            throw new BadRequestException("Password must contain at least 8 chars, " +
					"1 uppercase char, 1 lowercase char, a number, and a special char");

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

}