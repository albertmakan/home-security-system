package com.backend.admin.dto;

import com.backend.admin.model.auth.Role;
import com.backend.admin.model.auth.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private boolean blocked;
    private List<HouseholdDTO> households;

    public UserDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        if (user.getRoles() != null)
            roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        blocked = user.isBlocked();
    }

    public UserDTO(User user, boolean withHouseholds) {
        this(user);
        if (withHouseholds && user.getHouseholds() != null)
            households = user.getHouseholds().stream().map(HouseholdDTO::new).collect(Collectors.toList());
    }
}
