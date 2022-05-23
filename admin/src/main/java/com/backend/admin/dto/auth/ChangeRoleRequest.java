package com.backend.admin.dto.auth;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ChangeRoleRequest {
    @NotNull
    private ObjectId userId;
    @NotEmpty
    private List<String> roles;
}
