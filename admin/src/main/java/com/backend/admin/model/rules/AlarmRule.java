package com.backend.admin.model.rules;

import com.backend.admin.model.DeviceType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Document
public class AlarmRule {
    @Id @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private DeviceType deviceType;
    @NotNull
    private List<Condition> conditions;

    private String alarmText;
}
