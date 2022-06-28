package com.backend.admin.service;

import com.backend.admin.exception.BadRequestException;
import com.backend.admin.model.Log;
import com.backend.admin.repository.LogsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    public List<Log> loadAllWithSearchAndFilter(String keyword, String level, Date date) {
        LocalDate ld = date == null ? LocalDate.now() : toLocalDate(date);
        ObjectId idMin = new ObjectId(toDate(ld));
        ObjectId idMax = new ObjectId(toDate(ld.plusDays(1)));

        try {
            return level.equals("NO_VALUE") ? logsRepository.getForDayByFilter(idMin, idMax, keyword)
                    : logsRepository.getForDayByLevelAndFilter(idMin, idMax, level, keyword);
        } catch (Exception e) {
            throw new BadRequestException("Regular expression is invalid");
        }
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public void notify(Log log) {
        messagingTemplate.convertAndSend("/topic/warn-logs", log);
    }
}
