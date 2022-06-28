package com.backend.myhouse.services;

import com.backend.myhouse.model.Device;
import com.backend.myhouse.model.Household;
import com.backend.myhouse.model.Message;

import com.backend.myhouse.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

public class MessageReaderRunnable implements Runnable {
    private final DeviceService deviceService;
    private final Device device;
    private final Household household;
    private File file;

    private final MessageService messageService;

    public MessageReaderRunnable(Device device, Household household, DeviceService deviceService, MessageService messageService) {
        this.deviceService = deviceService;
        System.out.println("STARTING THREAD: " + device.getName());
        this.device = device;
        this.household = household;
        try {
            file = new ClassPathResource("devices" + this.device.getPath()).getFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.messageService = messageService;
    }

    @Override
    public void run() {
        System.out.println(device.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            processMessage(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
            deviceService.cancelTask(device.getId());
        }
    }

    private void processMessage(String message) {
        if (message == null)
            return;
        // TODO insert into drools and DB
        if (message.matches(device.getFilter())) {
            System.out.print("MATCH: ");
//            messageService.save(new Message(message, this.device, new Date()));
            deviceService.notifyUsers(household, message);
        }
        System.out.println(message);
    }
}
