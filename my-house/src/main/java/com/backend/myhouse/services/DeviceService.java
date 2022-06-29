package com.backend.myhouse.services;

import com.backend.myhouse.exception.NotFoundException;
import com.backend.myhouse.model.Device;
import com.backend.myhouse.model.Household;
import org.bson.types.ObjectId;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class DeviceService {
    @Autowired
    @Qualifier("rulesSession")
    private KieSession kieSession;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private HouseholdService householdService;

    private Map<ObjectId, ScheduledFuture<?>> tasks;
    
    @Autowired
    private MessageService messageService;

    @PostConstruct
    public void startThreads() {
        tasks = new HashMap<>();
        for (Household h : householdService.getAll()) {
            if (h.getDevices() == null) continue;
            for (Device d : h.getDevices()) {
                tasks.put(d.getId(), taskScheduler.scheduleAtFixedRate(new MessageReaderRunnable(d, this, messageService), d.getPeriod()));
            }
        }
        System.out.println("TASKS STARTED");
    }

    public void cancelTask(ObjectId deviceId) {
        if (tasks.get(deviceId).cancel(true))
            System.out.println("TASK CANCELLED");
    }

    // call from admin app when device is added
    public void addTask(ObjectId houseId, ObjectId deviceId) {
        Household household = householdService.findById(houseId).orElseThrow(() -> new NotFoundException("Household not found"));
        if (household.getDevices() == null) household.setDevices(new ArrayList<>());
        Device device = household.getDevices().stream().filter(d -> deviceId.equals(d.getId())).findAny()
                .orElseThrow(() -> new NotFoundException("Device not found"));
        tasks.put(device.getId(), taskScheduler.scheduleAtFixedRate(new MessageReaderRunnable(device, this, messageService), device.getPeriod()));
    }
}
