package com.backend.admin.service;

import com.backend.admin.model.rules.AlarmRule;
import com.backend.admin.repository.AlarmRuleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmRuleService {

    @Autowired
    private AlarmRuleRepository alarmRuleRepository;

    public List<AlarmRule> getAll() {
        return alarmRuleRepository.findAll();
    }

    public AlarmRule create(AlarmRule alarmRule) {
        return alarmRuleRepository.save(alarmRule);
    }

    public void delete(ObjectId id) {
        alarmRuleRepository.deleteById(id);
    }
}
