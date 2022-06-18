package com.backend.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HouseholdRequest {
    @NotBlank
    private String name;
}
