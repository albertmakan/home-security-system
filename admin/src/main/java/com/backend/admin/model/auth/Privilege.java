package com.backend.admin.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Privilege implements GrantedAuthority {
    private String name;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
}
