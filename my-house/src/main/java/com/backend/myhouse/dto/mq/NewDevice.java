package com.backend.myhouse.dto.mq;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class NewDevice {
    private ObjectId houseId, deviceId;
}
