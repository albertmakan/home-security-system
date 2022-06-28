package com.backend.admin.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class NewDevice {
    private ObjectId houseId, deviceId;
}
