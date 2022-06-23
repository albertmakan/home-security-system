package com.backend.admin.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.admin.model.Log;
import com.backend.admin.repository.LogsRepository;

@Service
public class CustomLogger {
    @Autowired
    private LogsRepository logsRepository;

    public String info(String message) {
        logsRepository.save(new Log("INFO", message));
        return message;
    }

    public String warn(String message) {
        logsRepository.save(new Log("WARN", message));
        return message;
    }

    public String error(String message) {
        logsRepository.save(new Log("ERROR", message));
        return message;
    }

    public List<Log> findAll() {
        return logsRepository.findAll();
    }

    public List<Log> loadAllWithSearchAndFilter(String keyword, Boolean regexChosen, String level, Date date) {

        System.out.println(keyword + regexChosen + level + date);
        List<Log> logs = logsRepository.findAll(); 
        
        if (!level.equals("NO_VALUE")){
            logs = logs.stream().filter(log -> log.getLevel().equals(level)).collect(Collectors.toList());
        }

        if (date != null){
            Timestamp ts = new Timestamp(date.getTime());
            //TODO
            // logs = logs.stream().filter(log -> log.getId().getTimestamp().equals(ts.get)).collect(Collectors.toList());

        }

        if (!keyword.isEmpty()){
            if (regexChosen){
                logs = logs.stream().filter(log -> log.getMessage().matches(keyword)).collect(Collectors.toList());
    
            }else{
                logs = logs.stream().filter(log -> log.getMessage().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    
            }
        }
        

        return logs;
    
    }
}
