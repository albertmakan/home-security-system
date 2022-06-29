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
    private final SignatureService signatureService;
    private final CustomLogger logger;

    public MessageReaderRunnable(Device device, Household household, DeviceService deviceService,
            MessageService messageService, SignatureService signatureService, CustomLogger logger) {
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
        this.signatureService = signatureService;
        this.logger = logger;
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

        try {
            String[] signedMessage = message.split("}");
            String msg = signedMessage[0];
            String signature = signedMessage[1];

            msg = msg + "}";
            signature = signature.trim();

            // TODO insert into drools and DB
            if (msg.matches(device.getFilter())) {
                if (signatureService.verify(msg, signature, device.getPublicKey())) {
                    messageService.save(new Message(msg, this.device, new Date()));
                    System.out.print("VERIFIED: ");
                    deviceService.notifyUsers(household, message);
                }
            }
            System.out.println(msg);
            logger.warn("POTENTIAL ATTACK! DEVICE PATH: " + device.getPath());
        } catch (Exception e) {
            return;
        }
    }
}
