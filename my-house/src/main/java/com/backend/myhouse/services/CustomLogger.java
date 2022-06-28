package com.backend.myhouse.services;

import com.backend.myhouse.dto.mq.MyHouseLog;
import com.backend.myhouse.model.Log;
import com.backend.myhouse.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomLogger {
    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    KafkaTemplate<String, MyHouseLog> kafkaTemplate;

    public String info(String message) {
        logsRepository.save(new Log("INFO", message));
        return message;
    }

    public String warn(String message) {
        logsRepository.save(new Log("WARN", message));
        kafkaTemplate.send("MY_HOUSE_LOG", new MyHouseLog(new Date(), "WARN", message));
        return message;
    }

    public String error(String message) {
        logsRepository.save(new Log("ERROR", message));
        kafkaTemplate.send("MY_HOUSE_LOG", new MyHouseLog(new Date(), "ERROR", message));
        return message;
    }

    public List<Log> findAll() {
        return logsRepository.findAll();
    }
}
