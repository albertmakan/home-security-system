package com.backend.myhouse.model;

import com.backend.myhouse.model.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Household {
    @Id
    private ObjectId id;

    private String name;

    private List<Device> devices;

    @DBRef(lazy = true)
    @JsonIgnore
    private List<User> users;
}
