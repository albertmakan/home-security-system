package com.backend.admin.service;

import com.backend.admin.model.Logs;
import com.backend.admin.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomLogger {
    @Autowired
    private LogsRepository logsRepository;

    public String info(String message) {
        logsRepository.save(new Logs("INFO", message));
        return message;
    }

    public String warn(String message) {
        logsRepository.save(new Logs("WARN", message));
        return message;
    }

    public String error(String message) {
        logsRepository.save(new Logs("ERROR", message));
        return message;
    }

    public List<Logs> findAll() {
        return logsRepository.findAll();
    }
}
