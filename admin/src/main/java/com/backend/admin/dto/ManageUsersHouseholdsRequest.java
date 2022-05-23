package com.backend.admin.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ManageUsersHouseholdsRequest {
    @NotNull
    private ObjectId userId;
    @NotNull
    private List<ObjectId> householdIds;
}
