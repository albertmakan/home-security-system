package com.backend.admin.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordRequest {
    @NotEmpty
    private String currentPassword;
    @NotEmpty
    private String newPassword;
}