package com.backend.admin.model.auth;

import com.backend.admin.model.Household;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Document
public class User implements UserDetails {
    @Id
    private ObjectId id;

    private String username;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private boolean enabled;

    private Date lastPasswordResetDate;

    @DBRef
    private List<Role> roles;

    private boolean blocked = false;

    private int loginAttempts = 0;
    private Date lastLoginAttemptDate;

    @DBRef(lazy = true)
    @JsonIgnore
    private List<Household> households;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> permissions = new ArrayList<>(20);
        for (Role role : this.roles) {
            permissions.addAll(role.getPrivileges());
        }
        return permissions;
    }

    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        setLastPasswordResetDate(now);
        this.password = password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
