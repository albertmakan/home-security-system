package com.backend.myhouse.controller;

import com.backend.myhouse.dto.mq.NewDevice;
import com.backend.myhouse.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @KafkaListener(
            topics = { "NEW_DEVICE" },
            groupId = "MyHouse",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void publishListener(NewDevice newDevice) {
        System.out.println("GOT NEW DEVICE!!!!!!!!!!!!!!!!!!!! "+newDevice.getDeviceId());
        deviceService.addTask(newDevice.getHouseId(), newDevice.getDeviceId());
    }
}
