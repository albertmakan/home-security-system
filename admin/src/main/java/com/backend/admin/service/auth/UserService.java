package com.backend.admin.service.auth;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.exception.BadRequestException;
import com.backend.admin.model.auth.Role;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.auth.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

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
		return userRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

    public User save(User user){
        return userRepository.save(user);
    }

	public User save(UserRequest userRequest) throws Exception {
		User u = new User();
		u.setUsername(userRequest.getUsername());

        //checking password strength
        String passRegex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        Pattern pattern = Pattern.compile(passRegex);
        Matcher matcher = pattern.matcher(userRequest.getPassword());

        if (!matcher.matches()){
            throw new BadRequestException("Password must contain atleast 8 chars, 1 uppercase char, 1 lowercae char, a number, and a special char");
        }
        
		
		// pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
		// treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		
		u.setFirstName(userRequest.getFirstname());
		u.setLastName(userRequest.getLastname());
		u.setEnabled(true);
		u.setEmail(userRequest.getEmail());

		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		List<Role> roles = roleService.findByName("ROLE_USER");
		u.setRoles(roles);
		
		return this.userRepository.save(u);
	}

    @Override
	public UserDetails loadUserByUsername(String username) throws BadRequestException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isEmpty()) {
			throw new BadRequestException(String.format("No user found with username '%s'.", username));
		} else {
			return (UserDetails) optionalUser.get();
		}
	}

}