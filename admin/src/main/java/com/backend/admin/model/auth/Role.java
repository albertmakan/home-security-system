package com.backend.admin.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Role implements GrantedAuthority {
    @Id
    @JsonIgnore
    private ObjectId id;

    private String name;

    private Set<Privilege> privileges;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }

}
