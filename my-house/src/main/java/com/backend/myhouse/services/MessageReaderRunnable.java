package com.backend.myhouse.services;

import com.backend.myhouse.model.Device;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MessageReaderRunnable implements Runnable {
    private final DeviceService deviceService;
    private final Device device;
    private File file;

    public MessageReaderRunnable(Device device, DeviceService deviceService) {
        this.deviceService = deviceService;
        System.out.println("STARTING THREAD: " + device.getName());
        this.device = device;
        try {
            file = new ClassPathResource("devices" + this.device.getPath()).getFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (message == null) return;
        //TODO insert into drools and DB
        if (message.matches(device.getFilter())) {
            System.out.print("MATCH: ");
        }
        System.out.println(message);
    }
}
