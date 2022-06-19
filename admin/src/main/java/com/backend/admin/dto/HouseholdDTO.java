package com.backend.admin.dto;

import com.backend.admin.model.Device;
import com.backend.admin.model.Household;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class HouseholdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private List<UserDTO> users;
    private List<Device> devices;

    public HouseholdDTO(Household household) {
        id = household.getId();
        name = household.getName();
        devices = household.getDevices();
    }

    public HouseholdDTO(Household household, boolean withUsers) {
        this(household);
        if (withUsers && household.getUsers() != null)
            users = household.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
