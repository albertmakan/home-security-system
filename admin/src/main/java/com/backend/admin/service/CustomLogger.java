package com.backend.admin.service;

import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.backend.admin.model.Log;
import com.backend.admin.repository.LogsRepository;

import lombok.AllArgsConstructor;
import com.backend.admin.exception.BadRequestException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomLogger {

    @Qualifier("rulesSession")
    private final KieSession rulesSession;

    private LogsRepository logsRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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
        notify(log);
        return message;

    }

    public String error(String message) {
        Log log = new Log("ERROR", message);
        rulesSession.getAgenda().getAgendaGroup("logs").setFocus();
        rulesSession.insert(log);
        rulesSession.fireAllRules();
        logsRepository.save(log);
        notify(log);
        return message;
    }

    public List<Log> findAll() {
        return logsRepository.findAll();
    }

    public List<Log> loadAllWithSearchAndFilter(String keyword, String level, Date date) {

        ObjectId idMin, idMax;

        if (date == null) {
            idMin = new ObjectId(new Date(0));
            idMax = new ObjectId(toDate(LocalDate.now().plusDays(1)));
        } else {
            LocalDate ld = toLocalDate(date);
            idMin = new ObjectId(toDate(ld));
            idMax = new ObjectId(toDate(ld.plusDays(1)));
        }

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
