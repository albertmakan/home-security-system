package com.backend.admin.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenState {
    private String accessToken;
    private Integer expiresIn;
}