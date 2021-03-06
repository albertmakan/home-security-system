package com.backend.myhouse.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document
public class Log {
    @Id @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String level;
    private String message;

    public Log(String level, String message) {
        this.level = level;
        this.message = message;
    }
}
