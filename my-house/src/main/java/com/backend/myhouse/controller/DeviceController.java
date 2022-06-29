package com.backend.myhouse.controller;

import com.backend.myhouse.dto.mq.NewDevice;
import com.backend.myhouse.services.AlarmRuleService;
import com.backend.myhouse.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AlarmRuleService alarmRuleService;

    @KafkaListener(
            topics = { "NEW_DEVICE" },
            groupId = "MyHouse",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void publishListener(NewDevice newDevice) {
        if (newDevice.getIsNewAlarmRule())
            alarmRuleService.createKieSession();
        else
            deviceService.addTask(newDevice.getHouseId(), newDevice.getDeviceId());
    }
}
