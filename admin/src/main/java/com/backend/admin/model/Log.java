package com.backend.admin.model;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;

import lombok.Data;

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
