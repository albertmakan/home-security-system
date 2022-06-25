package com.backend.myhouse.model;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
public class Message {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private Date timestamp;
    private String message;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId deviceId;

    public Message(String message, Device device, Date timestamp) {
        this.message = message;
        this.deviceId = device.getId();
        this.timestamp = timestamp;
    }

}
