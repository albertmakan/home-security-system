package com.backend.admin.service;

import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.backend.admin.model.Log;
import com.backend.admin.repository.LogsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomLogger {

    @Qualifier("rulesSession")
    private final KieSession rulesSession;
    
    private LogsRepository logsRepository;

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
        return message; 

    }

    public String error(String message) {
        Log log = new Log("ERROR", message);
        rulesSession.getAgenda().getAgendaGroup("logs").setFocus();
        rulesSession.insert(log);
        rulesSession.fireAllRules();
        logsRepository.save(log);
        return message;
    }

    public List<Log> findAll() {
        return logsRepository.findAll();
    }
}
