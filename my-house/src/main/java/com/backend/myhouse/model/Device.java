package com.backend.myhouse.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.persistence.Id;

@Data
public class Device {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String path;
    private Integer period;
    private String filter;
    private String publicKey;
    private DeviceType type;
}
