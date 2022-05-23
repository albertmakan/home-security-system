package com.backend.admin.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class UserRequest {
//	@NotBlank
//	private String username;
//	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",
//	message = "Password must contain at least 8 chars, " +
//			"1 uppercase char, 1 lowercase char, a number, and a special char")
//	private String password;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Email
	private String email;
	@NotEmpty
	private List<String> roles;
}
