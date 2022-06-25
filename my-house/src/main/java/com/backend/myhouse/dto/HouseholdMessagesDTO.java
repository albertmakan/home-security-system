package com.backend.myhouse.dto;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.backend.myhouse.model.Message;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HouseholdMessagesDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId householdId;
    private String householdName;
    private Map<String, String> deviceNames;
    private List<Message> messages;
}
