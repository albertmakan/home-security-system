package com.backend.admin.controller;

import com.backend.admin.dto.mq.MyHouseLog;
import com.backend.admin.model.Log;
import com.backend.admin.service.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogsController {
    @Autowired
    private CustomLogger logger;

    @GetMapping("/all")
    public ResponseEntity<List<Log>> loadAll() {
        return new ResponseEntity<>(logger.findAll(),HttpStatus.OK);
    }

    @KafkaListener(
            topics = { "MY_HOUSE_LOG" },
            groupId = "Admin",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void publishListener(MyHouseLog myHouseLog) {
        System.out.println("GOT My house log!!!!!!!!!!!!!!!!!!!! ");
        logger.notify(new Log(myHouseLog.getLevel(), myHouseLog.getMessage()));
    }
}
