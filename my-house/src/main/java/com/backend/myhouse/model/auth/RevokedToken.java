package com.backend.myhouse.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RevokedToken {
    @Id
    private ObjectId id;
    private String token;
    private Date date;
}
