package com.backend.admin.service.auth;

import com.backend.admin.dto.ManageUsersHouseholdsRequest;
import com.backend.admin.dto.auth.ChangePasswordRequest;
import com.backend.admin.dto.auth.ChangeRoleRequest;
import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.exception.BadRequestException;
import com.backend.admin.exception.NotFoundException;
import com.backend.admin.model.Household;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.auth.UserRepository;
import com.backend.admin.service.EmailService;
import com.backend.admin.service.HouseholdService;
import com.backend.admin.util.PasswordGenerator;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final RoleService roleService;

	private final HouseholdService householdService;

	private final EmailService emailService;

	@Setter
	private AuthenticationManager authenticationManager;
	@Setter
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleService roleService, HouseholdService householdService, EmailService emailService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.householdService = householdService;
		this.emailService = emailService;
	}

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

		u = save(u);
		emailService.sendEmail(u.getEmail(), "Smart home registration",
				"Your username: "+u.getUsername()+"\nYour password: "+generatedPassword
		);

		return u;
	}

	public void delete(ObjectId id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		if (user.getHouseholds() != null)
			user.getHouseholds().forEach(household -> householdService.removeUserFromHousehold(household.getId(), id));
		userRepository.deleteById(id);
	}

	public User changeRole(ChangeRoleRequest changeRoleRequest) {
		User user = userRepository.findById(changeRoleRequest.getUserId())
				.orElseThrow(() -> new NotFoundException("User not found"));
		user.setRoles(new ArrayList<>());

		for (String roleName : changeRoleRequest.getRoles())
			user.getRoles().addAll(roleService.findByName(roleName));

		return save(user);
	}

    @Override
	public UserDetails loadUserByUsername(String username) throws BadRequestException {
		Optional<User> optionalUser = findByUsername(username);
		return optionalUser.orElseThrow(() ->
				new BadRequestException(String.format("No user found with username '%s'.", username)));
	}

	public boolean changePassword(ChangePasswordRequest changePasswordDTO) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager == null)
			return false;
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, changePasswordDTO.getCurrentPassword()));

		User user = (User) loadUserByUsername(username);

		user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
		save(user);
		return true;
	}

	private String generateUsername(UserRequest user) {
		return user.getFirstName().replace(" ", "") + '.' +
				user.getLastName().replace(" ", "") +
				PasswordGenerator.generateNumberSequence(4);
	}

	public User manageHouseholds(ManageUsersHouseholdsRequest request) {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new NotFoundException("User not found"));
		if (user.getHouseholds() == null) user.setHouseholds(new ArrayList<>());

		Set<ObjectId> oldSet = user.getHouseholds().stream().map(Household::getId).collect(Collectors.toSet());
		Set<ObjectId> newSet = new HashSet<>(request.getHouseholdIds());
		newSet.removeAll(oldSet); // to add
		request.getHouseholdIds().forEach(oldSet::remove); // to delete

		for (ObjectId hid : newSet)
			householdService.findById(hid).ifPresent(h -> {
				householdService.addUserToHousehold(hid, user);
				user.getHouseholds().add(h);
			});
		for (ObjectId hid : oldSet)
			householdService.findById(hid).ifPresent(_h -> {
				householdService.removeUserFromHousehold(hid, user.getId());
				user.getHouseholds().removeIf(h -> hid.equals(h.getId()));
			});

		return save(user);
	}

}