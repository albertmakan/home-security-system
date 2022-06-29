package com.backend.myhouse.services;

import com.backend.myhouse.model.Device;
import com.backend.myhouse.model.Household;
import com.backend.myhouse.model.auth.User;
import org.bson.types.ObjectId;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    @Autowired
    private SignatureService signatureService;

    private Map<ObjectId, ScheduledFuture<?>> tasks;

    @Autowired
    private MessageService messageService;
    @Autowired
    public SimpMessagingTemplate messagingTemplate;
    @Autowired
    private CustomLogger logger;
    @Autowired
    private AlarmRuleService alarmRuleService;

    @PostConstruct
    public void startThreads() {
        tasks = new HashMap<>();
        for (Household h : householdService.getAll()) {
            if (h.getDevices() == null)
                continue;
            for (Device d : h.getDevices()) {
                tasks.put(d.getId(),
                        taskScheduler.scheduleAtFixedRate(
                                new MessageReaderRunnable(d, h, this, messageService, signatureService, logger, alarmRuleService),
                                d.getPeriod()));
            }
        }
        System.out.println("TASKS STARTED");
    }

    public void cancelTask(ObjectId deviceId) {
        if (tasks.get(deviceId).cancel(true))
            System.out.println("TASK CANCELLED");
    }

    public void addTask(ObjectId houseId, ObjectId deviceId) {
        Optional<Household> optionalHousehold = householdService.findById(houseId);
        if (!optionalHousehold.isPresent()) {
            System.out.println("Household not found " + houseId);
            return;
        }
        Household household = optionalHousehold.get();
        if (household.getDevices() == null)
            household.setDevices(new ArrayList<>());

        Optional<Device> optionalDevice = household.getDevices().stream().filter(d -> deviceId.equals(d.getId()))
                .findAny();
        if (!optionalDevice.isPresent()) {
            System.out.println("Device not found " + deviceId);
            return;
        }
        Device device = optionalDevice.get();
        tasks.put(device.getId(),
                taskScheduler.scheduleAtFixedRate(
                        new MessageReaderRunnable(device, household, this, messageService, signatureService, logger, alarmRuleService),
                        device.getPeriod()));

    }

    public void notifyUsers(Household household, String message) {
        if (household.getUsers() != null) {
            for (User user : household.getUsers()) {
                messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/alarms", message);
            }
        }

    }
}
