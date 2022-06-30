package com.backend.admin.dto;

import com.backend.admin.model.DeviceType;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeviceRequest {
    @NotNull
    private ObjectId householdId;
    @NotBlank
    private String name;
    @NotBlank
    private String path;
    @Min(1000)
    private Integer period;
    @NotBlank
    private String filter;
    @NotBlank
    private String publicKey;
    @NotNull
    private DeviceType type;
}
