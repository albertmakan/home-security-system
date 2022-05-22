package com.backend.admin.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
}
