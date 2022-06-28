package com.backend.admin.service;

import com.backend.admin.model.Log;
import com.backend.admin.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomLogger {
    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public String info(String message) {
        logsRepository.save(new Log("INFO", message));
        return message;
    }

    public String warn(String message) {
        Log l = new Log("WARN", message);
        logsRepository.save(l);
        notify(l);
        return message;
    }

    public String error(String message) {
        Log l = new Log("ERROR", message);
        logsRepository.save(l);
        notify(l);
        return message;
    }

    public List<Log> findAll() {
        return logsRepository.findAll();
    }

    public void notify(Log log) {
        messagingTemplate.convertAndSend("/topic/warn-logs", log);
    }
}
