package com.backend.admin.dto.mq;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class NewDevice {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId houseId, deviceId;
    private Boolean isNewAlarmRule;
}
