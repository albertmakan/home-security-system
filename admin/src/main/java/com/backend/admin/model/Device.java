package com.backend.admin.model;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.persistence.Id;

@Data
public class Device {
    @Id
    private ObjectId id;
    private String path;
    private Integer period;
    private String filter;
}
