package com.backend.admin.model;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.persistence.Id;

@Data
public class Log {
    @Id
    private ObjectId id;
    private String level;
    private String message;

    public Log(String level, String message) {
        this.level = level;
        this.message = message;
    }
}
