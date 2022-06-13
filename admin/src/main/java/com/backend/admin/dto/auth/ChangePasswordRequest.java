package com.backend.admin.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordRequest {
    @NotEmpty
    private String currentPassword;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",
            message = "Password must contain at least 8 chars, " +
                    "1 uppercase char, 1 lowercase char, a number, and a special char")
    private String newPassword;
}