package com.backend.myhouse.services;

import com.backend.myhouse.model.Device;
import com.backend.myhouse.model.Household;
import com.backend.myhouse.model.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Map;

public class MessageReaderRunnable implements Runnable {
    private final DeviceService deviceService;
    private final Device device;
    private final Household household;
    private File file;

    private final MessageService messageService;
    private final SignatureService signatureService;
    private final CustomLogger logger;
    private final AlarmRuleService alarmRuleService;

    private static final TypeReference<Map<String, Object>> typeRef
            = new TypeReference<Map<String, Object>>() {};

    public MessageReaderRunnable(Device device, Household household, DeviceService deviceService,
            MessageService messageService, SignatureService signatureService, CustomLogger logger,
                                 AlarmRuleService alarmRuleService) {
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
        this.alarmRuleService = alarmRuleService;
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

    private void processMessage(String messageStr) {
        try {
            String[] signedMessage = messageStr.split("}");
            String msg = signedMessage[0];
            String signature = signedMessage[1];

            msg = msg + "}";
            signature = signature.trim();

            if (msg.matches(device.getFilter())) {
                if (signatureService.verify(msg, signature, device.getPublicKey())) {
                    System.out.print("VERIFIED: ");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> map = mapper.readValue(msg, typeRef);
                    Message message = new Message(msg, this.device, new Date(), map);
                    message = alarmRuleService.evaluate(message);
                    if (message.getAlarm() != null)
                        deviceService.notifyUsers(household, message.getAlarm());

                    messageService.save(message);

                } else {
                    logger.warn("POTENTIAL ATTACK! DEVICE PATH: " + device.getPath());
                }
            }
            System.out.println(msg);

        } catch (Exception ignored) {
        }
    }
}
