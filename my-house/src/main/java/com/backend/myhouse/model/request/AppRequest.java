package com.backend.myhouse.model.request;

import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Role(Role.Type.EVENT)
public class AppRequest {
    private String ipAddress;
    private String requestURL;
    
}
