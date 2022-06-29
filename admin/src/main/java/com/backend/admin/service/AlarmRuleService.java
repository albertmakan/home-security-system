package com.backend.admin.service;

import com.backend.admin.dto.mq.NewDevice;
import com.backend.admin.model.rules.AlarmRule;
import com.backend.admin.repository.AlarmRuleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmRuleService {

    @Autowired
    private AlarmRuleRepository alarmRuleRepository;
    @Autowired
    private KafkaTemplate<String, NewDevice> kafkaTemplate;

    public List<AlarmRule> getAll() {
        return alarmRuleRepository.findAll();
    }

    public AlarmRule create(AlarmRule alarmRule) {
        AlarmRule ar = alarmRuleRepository.save(alarmRule);
        kafkaTemplate.send("NEW_DEVICE", new NewDevice(new ObjectId(), new ObjectId(), true));
        return ar;
    }

    public void delete(ObjectId id) {
        alarmRuleRepository.deleteById(id);
    }
}
