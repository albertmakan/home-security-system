package com.backend.admin.model;

import com.backend.admin.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {
    @Id
    private ObjectId id;

    private String email;

    private String password;

    private String salt;

    private String firstName;

    private String lastName;

    private boolean isConfirmed;

    private UserRole role;
}
