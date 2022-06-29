package com.backend.myhouse.services;

import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.backend.myhouse.model.Log;
import com.backend.myhouse.repository.LogsRepository;

import lombok.AllArgsConstructor;
import com.backend.myhouse.dto.mq.MyHouseLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomLogger {

    @Qualifier("rulesSession")
    private final KieSession rulesSession;
    
    private LogsRepository logsRepository;
    @Autowired
    KafkaTemplate<String, MyHouseLog> kafkaTemplate;

    public String info(String message) {
        Log log = new Log("INFO", message);
        rulesSession.getAgenda().getAgendaGroup("logs").setFocus();
        rulesSession.insert(log);
        rulesSession.fireAllRules();
        logsRepository.save(log);
        return message;
    }

    
    public String warn(String message) {
        Log log = new Log("WARN", message);
        rulesSession.getAgenda().getAgendaGroup("logs").setFocus();
        rulesSession.insert(log);
        rulesSession.fireAllRules();
        logsRepository.save(log);
        kafkaTemplate.send("MY_HOUSE_LOG", new MyHouseLog(new Date(), "WARN", message));
        return message; 

    }

    public String error(String message) {
        Log log = new Log("ERROR", message);
        rulesSession.getAgenda().getAgendaGroup("logs").setFocus();
        rulesSession.insert(log);
        rulesSession.fireAllRules();
        logsRepository.save(log);
        kafkaTemplate.send("MY_HOUSE_LOG", new MyHouseLog(new Date(), "ERROR", message));
        return message;
    }

    public List<Log> findAll() {
        return logsRepository.findAll();
    }
}
